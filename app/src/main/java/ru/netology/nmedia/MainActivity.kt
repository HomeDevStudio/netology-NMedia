package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + v.paddingLeft, systemBars.top + v.paddingTop,
                systemBars.right + v.paddingRight, systemBars.bottom + v.paddingBottom
            )
            insets
        }
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shareCount = 1900,
            viewsCount = 10_100_000
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = formatCount(post.likes)
            like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
            binding.like.setOnClickListener {
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
                likeCount.text = formatCount(post.likes)
            }
            shareCount.text = formatCount(post.shareCount)
            share.setOnClickListener {
                post.shareCount++
                shareCount.text = formatCount(post.shareCount)
            }
            viewsCount.text = formatCount(post.viewsCount)
        }
    }

    private fun formatCount(count: Int): String {
        val num = count.toDouble() / 1000
        return if (num in 1.0..999.9) {
            "${"%.1f".format(num).trimEnd { it == '0' }.trimEnd { it == ',' }}K"
        } else if (num > 999.9) {
            "${"%.1f".format(num / 1000).trimEnd { it == '0' }.trimEnd { it == ',' }}M"
        } else {
            count.toString()
        }
    }
}