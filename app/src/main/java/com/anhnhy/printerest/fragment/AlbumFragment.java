package com.anhnhy.printerest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anhnhy.printerest.R;
import com.anhnhy.printerest.adapter.ImageAdapter;
import com.anhnhy.printerest.model.Image;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment implements ImageAdapter.OnItemClickListener {
    Button btn_upload_img;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private ProgressBar progressCircle;
    private FirebaseStorage fbStorage;
    private DatabaseReference dbRef;
    private ValueEventListener valueEventListener;
    private List<Image> images;
    private String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference userRef;
    private List<String> imageIds;

    public AlbumFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_upload_img = view.findViewById(R.id.btn_upload_img);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        progressCircle = view.findViewById(R.id.progress_circle);

        images = new ArrayList<>();
        imageIds = new ArrayList<>();
        adapter = new ImageAdapter(getContext(), images);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        fbStorage = FirebaseStorage.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference("images");
        userRef = FirebaseDatabase.getInstance().getReference("users").child(UID);
        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference imageIdsRef = userRef.child("imageIds");
                valueEventListener = imageIdsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String imageId = dataSnapshot.getValue().toString();
                            imageIds.add(imageId);
                        }
                        images.clear();
                        for (String imageId : imageIds) {
                            DatabaseReference imageRef = dbRef.child(imageId);
                            valueEventListener = imageRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = snapshot.child("name").getValue().toString();
                                    String imageUrl = snapshot.child("imageUrl").getValue().toString();
                                    String senderId = snapshot.child("senderId").getValue().toString();
                                    Image image = new Image(name, imageUrl, senderId);
                                    image.setKey(imageId);
                                    images.add(image);
                                    adapter.notifyDataSetChanged();
//                            progressCircle.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                            progressCircle.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                        imageIds.clear();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(getContext(), "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Image selectedItem = images.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = fbStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dbRef.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
