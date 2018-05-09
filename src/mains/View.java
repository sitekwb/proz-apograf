package mains;

import views.InitView;

public class View {
    protected InitView initView;
    protected View(){}

    public View(Controller cont){
        initView = new InitView(cont);

    }
}
