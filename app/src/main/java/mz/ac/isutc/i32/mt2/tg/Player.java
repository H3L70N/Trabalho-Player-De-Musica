package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.getRepeat;
import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Random;

import mz.ac.isutc.i32.mt2.tg.databinding.ActivityPlayListBinding;
import mz.ac.isutc.i32.mt2.tg.databinding.ActivityPlayerBinding;
import mz.ac.isutc.i32.mt2.tg.databinding.PlayerBinding;

public class Player extends AppCompatActivity {

    private NotificationCompat.Builder notificationBuilder;
    private static MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder stateBuilder;
    private NotificationManager notificationManager;
    ActivityPlayerBinding b;
    int indice;
    static MediaPlayer mediaPlayer;
    Musica musica;
    Thread updateSeekBar;

    ArrayList<Musica> musicas;
    ArrayList<Musica> musicas_temp;

    SharedViewModel v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        inicializar();
    }

    void inicializar(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }



        Bundle bundle = getIntent().getExtras();
        if(bundle.size() > 1){
            String categoria = bundle.getString("categoria");
            musicas = new ArrayList<>();
            for(Musica musica : musicas_db){
                if(musica.getCategoria() != null){
                    if(musica.getCategoria().getNome().equals(categoria)){
                        musicas.add(musica);
                    }
                }
            }

        }else{
            musicas = musicas_db;
        }
        musicas_temp = musicas;
        indice = bundle.getInt("indice");
        //shuffleOn(indice);

        musica = musicas.get(indice);
        Uri uri = Uri.parse(musica.getMusicFile().toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

//        b.shufflePlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(shuffle){
//                    b.shufflePlayer.setBackgroundResource(R.drawable.baseline_shuffle_24);
//                    shuffle = false;
//                    //shuffleOn(0);
//                }else{
//                    b.shufflePlayer.setBackgroundResource(R.drawable.baseline_shuffle_on_24);
//                    shuffle = true;
//                    //shuffleOn(musicas_db.indexOf(indice));
//                }
//
//            }
//        });

        b.seekbarPlayer.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        b.seekbarPlayer.getThumb().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);


        carregarInfo(musica);
        b.seekbarPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        b.nextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                indice = ((indice+1)%musicas.size());
                musica = musicas.get(indice);
                Uri u = Uri.parse(musica.getMusicFile().toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                mediaPlayer.start();
                startAnimation(b.imageMusicPlayer);
                carregarInfo(musica);
            }
        });

        b.previousPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                indice = ((indice-1)<0)? (musicas.size()-1):(indice-1);
                musica = musicas.get(indice);
                Uri u = Uri.parse(musica.getMusicFile().toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                mediaPlayer.start();
                startAnimation(b.imageMusicPlayer);
                carregarInfo(musica);
            }
        });

        b.forwardPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = mediaPlayer.getDuration();
                int position = mediaPlayer.getCurrentPosition();
                if ((position + 10000) > duration){
                    b.nextPlayer.performClick();
                }else{
                    mediaPlayer.seekTo(position + 10000);
                }
            }
        });

        b.replayPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = mediaPlayer.getCurrentPosition();
                if((duration - 10000) < 0){
                    mediaPlayer.seekTo(0);
                }else{
                    mediaPlayer.seekTo(duration-10000);
                }
            }
        });



        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(indice+1 ==  musicas.size()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    finish();
                }else{
                    b.nextPlayer.performClick();
                }
            }
        });




    }

    public void createMediaSeccion() {
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
                b.playPlayer.performClick();
            }

            @Override
            public void onPause() {
                super.onPause();
                b.playPlayer.performClick();
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();

            }
        });
    }

    void carregarInfo(Musica musica){
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while(currentPosition<totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        b.seekbarPlayer.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();

                    }
                }
            }
        };
        b.playPlayer.setBackgroundResource(R.drawable.baseline_pause_24);
        indice = musicas.indexOf(musica);
        b.playPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    b.playPlayer.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer.pause();
                }else{
                    b.playPlayer.setBackgroundResource(R.drawable.baseline_pause_24);
                    mediaPlayer.start();
                }
            }
        });

        try{
            Bitmap songImage = BitmapFactory
                    .decodeByteArray(musica.getArt(), 0, musica.getArt().length);
            b.imageMusicPlayer.setImageBitmap(songImage);
        }catch (Exception e){}

        b.autorPlay.setText(musica.getArtista());
        String categoria;
        if(musica.getCategoria() == null){
            categoria = "Sem Categoria";
        }else{
            categoria = musica.getCategoria().getNome();
        }

        b.categoriaPlayer.setText(categoria);
        b.tituloPlay.setText(musica.getTitulo());
        b.finalPlayer.setText(createTime(mediaPlayer.getDuration()));

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.startPlayer.setText(createTime(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this, delay);
            }
        }, delay);


        b.seekbarPlayer.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();

    }

    void startAnimation(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(b.imageMusicPlayer, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animatorSet.start();

    }

    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time += min +":";
        if(sec < 10){
            time += "0";
        }
        time += sec;

        return time;
    }

}