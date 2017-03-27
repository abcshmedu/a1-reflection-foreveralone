import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.reflection.ReflectiveRenderer;
import edu.hm.weidacher.softarch.reflection.Renderer;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class RendererTest {

    private Object sut;
    private Renderer renderer;

    @Before
    public void setup() {
        sut = new Testinger();
        renderer = new ReflectiveRenderer(sut);
    }

    @Test
    public void printTest() {
	System.out.println(renderer.render());
    }

    class Testinger {
        int xInt;
        long xLong;
        String xString;
        double xDouble;
        float xFloat;

	public Testinger() {
	    xInt = ((int) Math.random()*Integer.MAX_VALUE);
	    xLong = ((long) (Math.random() * Long.MAX_VALUE));
	    xString = "spadigusfgonuadsgvpisdfbng";
	    xDouble = Math.random();
	    xFloat = ((float) Math.random());
	}
    }

}
