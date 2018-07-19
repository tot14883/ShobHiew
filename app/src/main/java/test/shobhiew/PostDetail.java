package test.shobhiew;

import java.util.ArrayList;
import java.util.List;

public class PostDetail {

    int imgId;
    String nameUser;
    String detail;
    int picPost;

    PostDetail (int imgId,String nameUser,String detail,int picPost) {

        this.imgId = imgId;
        this.nameUser = nameUser;
        this.detail= detail;
        this.picPost= picPost;

    }

    private List<PostDetail> postDetail;

    private void initializeData(){
        postDetail = new ArrayList<>();
        postDetail.add(new PostDetail(R.drawable.kylo, "kylo Ren", "fdsfdsfsdfsdfsdfsdfsdfdsf",R.drawable.kylo));
        postDetail.add(new PostDetail(R.drawable.space, "Space Man", "fdsfdsfsdfsdfsdfsdfsdfdsf",R.drawable.space));
        postDetail.add(new PostDetail(R.drawable.omen, "omen Man ", "fdsfdsfsdfsdfsdfsdfsdfdsf",R.drawable.omen));
    }






}
