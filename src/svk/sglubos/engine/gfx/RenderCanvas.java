/*
 *	Copyright 2015 ¼ubomír Hlavko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import svk.sglubos.engine.utils.debug.MessageHandler;

@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	protected double scale = 1.0;
	
	protected BufferedImage renderLayer;
	protected BufferStrategy bs;
	
	public RenderCanvas(Screen screen,double scale){
		renderLayer = screen.getRenderLayer();
		setPreferredSize(new Dimension((int)(screen.getWidth()*scale), (int)(screen.getHeight()*scale)));
		
		this.scale = scale;
	}
	
	public void init(int numBuffers){
		try{
			createBufferStrategy(numBuffers);			
		}catch(Exception e){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "Exception while creating BufferStrategy ! printing stack trace\n");
			e.printStackTrace();
		}
		bs = getBufferStrategy();
	}
	
	public void showRenderedContent(){
		if(bs == null){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "BufferStrategy is not initialized !");
			return;
		}
		
		Graphics g = null;
		
		do {
		    try{
		    	g = bs.getDrawGraphics();
		    	g.drawImage(renderLayer, 0, 0,getWidth(),getHeight(), null);
		    } finally {
		    	if(g != null)
		    		g.dispose();
		    }
		    bs.show();
		} while (bs.contentsLost());
	}
	
}
