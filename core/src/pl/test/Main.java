package pl.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer g;
	float x=0,y=0;
	float velx=0,vely=0;
	BitmapFont font;
	int[][] tab =   new int[][]{{2,1,0,1,0,0,1,0,0,0},
							    {0,0,0,0,1,0,0,0,1,0},
							    {1,1,1,0,0,1,1,0,1,0},
							    {1,0,0,0,1,0,1,0,1,0},
							    {1,0,1,0,1,1,1,1,1,0},
							    {1,0,1,0,0,1,0,0,0,0},
								{1,1,1,1,1,1,1,1,1,1}};
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("img.png");
		g = new ShapeRenderer();
		font  = new BitmapFont();
		for (int i = 0; i< tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				if(tab[i][j]==2) {
					x=i*64;
					y=j*64;
				}

			}
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//batch.draw(img, 0, 0);
		int accy = (int) Math.floor(Gdx.input.getAccelerometerX());
		int accx = (int) Math.floor(Gdx.input.getAccelerometerY());
		if(Math.abs(accx)>2)velx += accx/10f;
		if(Math.abs(accy)>2)vely += -accy/10f;
		velx = (Math.abs(velx)>7)? ((velx>0)? 7 : -7): velx;
		vely = (Math.abs(vely)>7)? ((vely>0)? 7 : -7): vely;
		//friction
		velx+=velx*-0.1;
		vely+=vely*-0.1;
		x+=velx;
		y+=vely;

		batch.begin();
		//float accz = (float) Math.floor(Gdx.input.getAccelerometerZ()*100)/100;
		font.draw(batch,"test: X;"+accx+" Y:"+accy+" ",100,100);
		font.setColor(Color.WHITE);
		batch.end();
		g.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i< tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				if(tab[i][j]==1) {
					g.setColor(Color.WHITE);
					g.rect(i * 64, j * 64, 64, 64);
				}

			}
		}
		g.setColor(Color.GREEN);
		g.rect(x,y,64,64);
		g.end();

		if(x<0){
			x=0;velx=0;
		}
		if(y<0){
			y=0;vely=0;
		}
		if(y+64>Gdx.graphics.getHeight()){
			y=Gdx.graphics.getHeight()-64;vely=0;
		}
		if(x+64>Gdx.graphics.getWidth()){
			x=Gdx.graphics.getWidth()-64;velx=0;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
