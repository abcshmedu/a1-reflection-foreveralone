import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.reflection.ArrayRenderer;
import edu.hm.weidacher.softarch.reflection.ReflectiveRenderer;
import edu.hm.weidacher.softarch.reflection.Renderer;
import edu.hm.weidacher.softarch.reflection.annotation.RenderMe;

/**
 * @author Simon Weidacher <weidache@hm.edu>
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

   // @Test
    public void arrayPrintTest() {
	final int[] ints = new int[100];
	System.out.println(new ArrayRenderer(ints).render());
    }

    class Testinger {
        @RenderMe int xInt;
        @RenderMe long xLong;
        @RenderMe String xString;
        @RenderMe double xDouble;
        @RenderMe float xFloat;
        @RenderMe(with = "edu.hm.weidacher.softarch.reflection.ArrayRenderer") int[] xInts;
        @RenderMe(with = "edu.hm.weidacher.softarch.reflection.ArrayRenderer") Object[] xObjs;

	public Testinger() {
	    xInt = (int) (Math.random()*Integer.MAX_VALUE);
	    xLong = ((long) (Math.random() * Long.MAX_VALUE));
	    xString = "spadigusfgonuadsgvpisdfbng";
	    xDouble = Math.random();
	    xFloat = ((float) Math.random());
	    xInts = new int[] {
	        100, 200, 300, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.SIZE
	    };
	    xObjs = new Object[2];
	}
    }

}
