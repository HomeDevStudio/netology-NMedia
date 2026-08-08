package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias ClickListener = (Post) -> Unit

class PostAdapter(
    private val likeClickListener: ClickListener,
    private val shareClickListener: ClickListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder = PostViewHolder(
        CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        likeClickListener,
        shareClickListener
    )

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeClickListener: ClickListener,
    private val shareClickListener: ClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = formatCount(post.likes)
            like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
            shareCount.text = formatCount(post.shareCount)
            viewsCount.text = formatCount(post.viewsCount)

            like.setOnClickListener {
                likeClickListener(post)
            }

            share.setOnClickListener {
                shareClickListener(post)
            }
        }
    }
}

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean = oldItem == newItem

    override fun getChangePayload(
        oldItem: Post,
        newItem: Post
    ): Any = Unit
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