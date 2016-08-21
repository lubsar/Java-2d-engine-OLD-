//THIN MATRIX ©

package svk.sglubos.engine.test;
 
import java.util.Random;
 
public class Noiser {
 
    private static final float AMPLITUDE = 1f;
    public static final int OCTAVES = 1;
    private static final float ROUGHNESS = 0.33f;
 
    private Random random = new Random();
    private int seed;
    private int xOffset = 0;
 
    public Noiser() {
        this.seed = random.nextInt(1000000000);
    }
     
    //only works with POSITIVE gridX and gridZ values!
    public Noiser(int gridX, int gridZ, int vertexCount, int seed) {
        this.seed = seed;
        xOffset = gridX * (vertexCount-1);
    }
 
    public float generateHeight(int x) {
        float total = 0;
        float d = (float) Math.pow(2, OCTAVES-1);
        for(int i=0;i<OCTAVES;i++){
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getInterpolatedNoise((x+xOffset)*freq) * amp;
        }
        return total;
    }
     
    private float getInterpolatedNoise(float x){
        int intX = (int) x;
        float fracX = x - intX;
         
        float v1 = getSmoothNoise(intX);
        float v2 = getSmoothNoise(intX + 1);
        float v3 = getSmoothNoise(intX - 1);
        float i1 = interpolate(v1, v2, 9f);
        float i2 = interpolate(v1, v3, 9f);
        return interpolate(i1, i2, 1.9f);
    }
     
    private float interpolate(float a, float b, float blend){
        double theta = blend * Math.PI;
        float f = (float)(1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }
 
    private float getSmoothNoise(int x) {
        float sides = (getNoise(x - 1) + getNoise(x + 1)) / 8f;
        float center = getNoise(x) / 4f;
        return sides + center;
    }
 
    private float getNoise(int x) {
        random.setSeed(x * 49632 + seed);
        return random.nextFloat();
    }
 
}
