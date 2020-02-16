package poly;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest {

	@Test
	public void test01_ctr_noarg() {
		Polynomial p = new Polynomial();
		assertTrue("Degree must be zero!", p.test_getDegree() == 0);
		double[] a = p.test_getCf();
		assertTrue("There should be one coefficient!", a.length == 1);
		assertTrue("Coefficient must be zero!", Double.doubleToLongBits(a[0]) == 0);
	}

	@Test
	public void test02_ctr_arg() {
		double a = 2.0;
		int i = 3;
		Polynomial p = new Polynomial(a, i);
		assertTrue("Degree must be " + i , p.test_getDegree() == i);
		double[] cf = p.test_getCf();
		assertTrue("There should be " + (i + 1) + " coefficients", cf.length == i + 1);
		assertTrue("Coefficient " + i + " must be " + a, Double.doubleToLongBits(cf[i]) == Double.doubleToLongBits(a));
	}

	@Test
	public void test03_ctr_arr() {
		double[] a = new double[] { 1, 3, 2, 6 };
		Polynomial p = new Polynomial(a);
		assertTrue("Degree must be 3", p.test_getDegree() == 3);
		double[] cf = p.test_getCf();
		assertTrue("There should be 4 coefficients", cf.length == 4);
		for (int i = 3; i >= 0; i--)
			assertTrue("Coefficient " + i + " must be " + a[i],
					Double.doubleToLongBits(cf[i]) == Double.doubleToLongBits(a[i]));
	}

	@Test
	public void test04_equals() {
		double[] a = new double[] { 1, 3, 2, 6 };
		double[] b = new double[] { 1, 3, 2, 6 };
		Polynomial p1 = new Polynomial(a);
		assertTrue("Polynomials P1 must be equal to itself", p1.equals(p1));
		Polynomial p2 = new Polynomial(b);
		assertTrue("Polynomials P1 and P2 must be equal", p1.equals(p2));
		p2 = new Polynomial(3, 3);
		assertFalse("Polynomials P1 and P2 must not be equal", p1.equals(p2));
		assertFalse("Polynomials P1 and P2 must not be equal", p1.equals(new Polynomial()));
		assertFalse("Polynomials P1 must not be equal to null", p1.equals(null));
		assertFalse("Polynomials P1 must not be equal to non-polynomial object", p1.equals(new Object()));
	}

	@Test
	public void test05_getDegree() {
		double[] a = new double[] { 1, 3, 2, 6 };
		Polynomial p1 = new Polynomial(a);
		int n = p1.getDegree();
		assertTrue("Wrong polynomial degree, expected 3 but " + n + " was returned", n == 3);
	}

	@Test
	public void test06_hashCode() {
		double[] a = new double[] { 1, 3, 2, 6 };
		double[] b = new double[] { 1, 3, 2, 6 };
		Polynomial p1 = new Polynomial(a);
		Polynomial p2 = new Polynomial(b);
		assertTrue("hashcode is inconsistant", p1.hashCode() == p1.hashCode());
		assertTrue("hashcodes are not equal", p1.hashCode() == p2.hashCode());
		a = p2.test_getCf();
		a[0] = 3;
		assertFalse("hashcodes shall not be equal", p1.hashCode() == p2.hashCode());
	}

	@Test
	public void test07_compareTo() {
		double[] a = new double[] { 1, 3, 2, 6 };
		double[] b = new double[] { 1, 3, 2, 6 };
		Polynomial p1 = new Polynomial(a);
		assertTrue("compareTo: Polynomials P1 must be equal to itself", p1.compareTo(p1) == 0);
		Polynomial p2 = new Polynomial(b);
		assertTrue("compareTo: Polynomials P1 and P2 must be equal", p1.compareTo(p2) == 0);
		a = p2.test_getCf();
		a[0] = a[0] * 2;
		assertFalse("compareTo: Polynomial P2 must be greater than P1", p1.compareTo(p2) > 0);
		a[0] = -1;
		assertTrue("compareTo: Polynomial P1 must be greater than P2", p1.compareTo(p2) > 0);
		assertFalse("compareTo: Polynomial P2 must be greater than P1", p1.compareTo(new Polynomial(3, 3)) < 0);
		assertTrue("compareTo: Polynomial P1 must be greater than P2", p1.compareTo(new Polynomial(3, 2)) > 0);
		assertTrue("compareTo: Polynomials P1 and P2 must not be equal", p1.compareTo(new Polynomial()) > 0);
		assertTrue("compareTo: Polynomial P2 must be greater than P1", p1.compareTo(new Polynomial(3, 4)) < 0);
	}

	@Test
	public void test08_evaluate() {
		double[] a = new double[] { 1, 3, 2, 6 };
		Polynomial p1 = new Polynomial(a);
		double x = 2;
		double y = p1.evaluate(x);
		double p = 1 + x * (3 + x * (2 + 6 * x));
		assertTrue("Wrong polynomial function value returned in the evaluate method\n" + "Expected: " + p
				+ " Returned: " + y, y == p);
	}

	@Test
	public void test09_plus() {
		Polynomial p = new Polynomial(new double[] { 1, 3, 2, 6 });
		Polynomial q = new Polynomial(new double[] { 0, 1, -2, -3 });
		Polynomial w_ret = p.plus(q);
		Polynomial w_exp = new Polynomial(new double[] { 1, 4, 0, 3 });
		assertTrue("Wrong polynomial addition", w_exp.equals(w_ret));
	}

	@Test
	public void test10_minus() {
		Polynomial p = new Polynomial(new double[] { 1, 3, 2, 6 });
		Polynomial q = new Polynomial(new double[] { 0, 1, -2, -3 });
		Polynomial w_ret = p.minus(q);
		Polynomial w_exp = new Polynomial(new double[] { 1, 2, 4, 9 });
		assertTrue("Wrong polynomial subtraction", w_exp.equals(w_ret));
	}

	@Test
	public void test11_times() {
		Polynomial p = new Polynomial(new double[] { 1, 3, 2, 6 });
		Polynomial q = new Polynomial(new double[] { 0, 1, -2, -3 });
		Polynomial w_ret = p.times(q);
		Polynomial w_exp = new Polynomial(new double[] { 0, 1, 1, -7, -7, -18, -18 });
		assertTrue("Wrong polynomial multiplication", w_exp.equals(w_ret));
	}

	@Test
	public void test12_compose() {
		Polynomial p = new Polynomial(new double[] { 1, 3, 2, 6 });
		Polynomial q = new Polynomial(new double[] { 0, 1, -2, -3 });
		Polynomial w_ret = p.compose(q);
		Polynomial w_exp = new Polynomial(new double[] { 1, 3, -4, -11, -40, 42, 186, -54, -324, -162 });
		assertTrue("Wrong polynomial composition", w_exp.equals(w_ret));
	}

	@Test
	public void test13_derive() {
		Polynomial p = new Polynomial(new double[] { 1, 3, 2, 6 });
		Polynomial w_ret = p.derive();
		Polynomial w_exp = new Polynomial(new double[] { 3, 4, 18});
		assertTrue("Wrong polynomial derivation", w_exp.equals(w_ret));
	}

	@Test
	public void test14_toString() {
		String[] s_exp = new String[] { "0.0", "1.0", "1.0x", "1.0x - 1.0", "2.0x^2", "2.0x^2 + 1.0x", "2.0x^2 - 1.0",
				"2.0x^2 + 1.0x + 1.213E-4" };
		Polynomial[] p = new Polynomial[] { new Polynomial(), new Polynomial(1, 0), new Polynomial(1, 1),
				new Polynomial(new double[] { -1, 1 }), new Polynomial(new double[] { 0, 0, 2 }),
				new Polynomial(new double[] { 0, 1, 2 }), new Polynomial(new double[] { -1, 0, 2 }),
				new Polynomial(new double[] { 0.0001213, 1, 2 }) };
		for (int i = 0; i < s_exp.length; i++) {
			String msg = "Expected " + s_exp[i] + " but " + p[i].toString() + " was returned";
			assertEquals(msg, s_exp[i], (p[i]).toString());
		}
	}
}
