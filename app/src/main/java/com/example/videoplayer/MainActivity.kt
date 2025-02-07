package com.example.videoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnPlay: Button
    private lateinit var btnPause: Button
    private lateinit var btnStop: Button
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button

    private val videoList = listOf(
        R.raw.video1,
        R.raw.video2,
        R.raw.video3// Cambia estos nombres por los de tus videos en res/raw
    )
    private var currentVideoIndex = 0  // Índice del video actual

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        btnPlay = findViewById(R.id.button)
        btnPause = findViewById(R.id.button3)
        btnStop = findViewById(R.id.button2)
        btnPrev = findViewById(R.id.button4)
        btnNext = findViewById(R.id.button5)

        // Elimina el MediaController para no usar los controles predeterminados
        // videoView.setMediaController(mediaController) // Ya no es necesario

        loadVideo(videoList[currentVideoIndex])

        // Botón Play
        btnPlay.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.start()
            }
        }

        // Botón Pause
        btnPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }

        // Botón Stop (reinicia el video)
        btnStop.setOnClickListener {
            videoView.stopPlayback()
            videoView.resume()
            loadVideo(videoList[currentVideoIndex])
        }

        // Botón Anterior
        btnPrev.setOnClickListener {
            if (currentVideoIndex > 0) {
                currentVideoIndex--
                loadVideo(videoList[currentVideoIndex])
            } else {
                Toast.makeText(this, "No hay más videos anteriores", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Siguiente
        btnNext.setOnClickListener {
            if (currentVideoIndex < videoList.size - 1) {
                currentVideoIndex++
                loadVideo(videoList[currentVideoIndex])
            } else {
                Toast.makeText(this, "No hay más videos siguientes", Toast.LENGTH_SHORT).show()
            }
        }

        // Detectar cuando el video finaliza
        videoView.setOnCompletionListener {
            Toast.makeText(this, "Video finalizado", Toast.LENGTH_SHORT).show()
        }
    }

    // Cargar un video desde res/raw
    private fun loadVideo(videoResId: Int) {
        val videoUri = Uri.parse("android.resource://$packageName/$videoResId")
        videoView.setVideoURI(videoUri)
        videoView.requestFocus()
        videoView.start()
    }
}
