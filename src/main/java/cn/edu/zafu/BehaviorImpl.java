package cn.edu.zafu;

/**
 * Created by lizhangqu on 16/4/28.
 */
public class BehaviorImpl implements IBehavior {
    @Override
    public void perform(String behaiviorName, String behaiviorContent) {
        System.out.println("behaiviorName:" + behaiviorName);
        System.out.println("behaiviorContent:" + behaiviorContent);
    }
}
