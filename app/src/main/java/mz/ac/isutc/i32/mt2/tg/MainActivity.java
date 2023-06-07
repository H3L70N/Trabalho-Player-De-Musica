package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.buscarMusicas;
import static mz.ac.isutc.i32.mt2.tg.DB.categorias_db;
import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {


    private NotificationCompat.Builder notificationBuilder;
    private static MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder stateBuilder;
    private NotificationManager notificationManager;
    public static final String CHANNEL_ID = "channelId";

    MainBinding b;
    DB db;
    private SharedViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = MainBinding.inflate(getLayoutInflater());
        runTimePermission();
        setContentView(b.getRoot());

        createNotificationChannel();
        inicializar();


    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    public void createMediaSeccion(){
        mediaSessionCompat = new MediaSessionCompat(this, "simplePlayer session");
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );
        mediaSessionCompat.setMediaButtonReceiver(null);
        mediaSessionCompat.setPlaybackState(stateBuilder.build());
        mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
            }

            @Override
            public void onPause() {
                super.onPause();
            }
        });
    }

    void inicializar(){



        final ArrayList<File> files = buscarMusicas(Environment.getExternalStorageDirectory());
        PlayList f2 = new PlayList(files, true);
        db = new DB(this);
        categorias_db = db.ler();


        Categoria f1 = new Categoria();

        ArrayList<MeuFragment> fragments = new ArrayList<>();

        fragments.add(f1);
        fragments.add(f2);

        MeuPageAdapter adapter = new MeuPageAdapter(getSupportFragmentManager(), fragments);
        b.pager.setAdapter(adapter);
        viewModel =  new ViewModelProvider(this).get(SharedViewModel.class);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.gravar();
    }

    public void runTimePermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        inicializar();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

}