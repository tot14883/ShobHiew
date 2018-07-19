package test.shobhiew;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PostRecycleAdapter extends RecyclerView.Adapter<PostRecycleAdapter.PostDetailViewHolder> {

    @NonNull
    @Override
    public PostDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_feed, parent, false);
//        PersonViewHolder pvh = new PersonViewHolder(v);
        return new PostDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailViewHolder holder, int position) {
        holder.imgUser.setImageResource(postDetail.get(position).imgId);
        holder.nameUser.setText(postDetail.get(position).nameUser);
        holder.detailProduct.setText(postDetail.get(position).detail);
        holder.imgProduct.setImageResource(postDetail.get(position).picPost);

    }

    @Override
    public int getItemCount() {
        return postDetail.size();
    }

    public static class PostDetailViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewPost;
        ImageView imgUser;
        TextView nameUser;
        TextView detailProduct;
        ImageView imgProduct;

        PostDetailViewHolder(View itemView) {
            super(itemView);
            cardViewPost = (CardView)itemView.findViewById(R.id.post_feed);
            imgUser = (ImageView) itemView.findViewById(R.id.profile_image);
            nameUser = (TextView) itemView.findViewById(R.id.nameUser);
            detailProduct = (TextView)itemView.findViewById(R.id.detailProduct);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
        }

    }

    List<PostDetail> postDetail;

    public PostRecycleAdapter(List<PostDetail> postDetail){
        this.postDetail = postDetail;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
