package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteGraph extends fr.cva.ldnr.runningstats.Menu  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphique);
    }

    public void seehisto (View view) {
        Intent intent = new Intent(this, ActiviteHistorique.class);
        startActivity(intent);
    }




}
