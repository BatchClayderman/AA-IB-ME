import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Utils
{
    public static Element delta(Element x, Element i, Element[] S, PARS pars)
    {
        Element result = pars.getZp().newOneElement().getImmutable();
        for (Element j : S)
            if (!i.duplicate().equals(j.duplicate()))
                result = result.mul(x.duplicate().sub(j.duplicate()).div(i.duplicate().sub(j.duplicate()))).getImmutable();
        return result;
    }

    public static Element T(Element x, PARS pars)
    {
        Element[] t = pars.getT();
        int n = t.length;
        Element result = pars.getG().newOneElement().getImmutable();
        Element[] N = new Element[n];
        for (int i = 0; i < n; ++i)
            N[i] = pars.getZp().newElement(BigInteger.valueOf(i + 1)).getImmutable();
        for (int i = 0; i < n; ++i)
            result = result.duplicate().mul(t[i].duplicate().powZn(delta(x, pars.getZp().newElement(BigInteger.valueOf(i + 1)), N, pars))).getImmutable();
        result = result.duplicate().mul(pars.getG2().duplicate().powZn(x.duplicate().pow(BigInteger.valueOf(n - 1))));
        return result;
    }

    /*
    public static Element H(Element x, PARS pars)
    {
        Element[] l = pars.getL();
        int n = l.length;
        Element result = pars.getG().newOneElement().getImmutable();
        Element[] N = new Element[n];
        for (int i = 0; i < n; i++) {
            N[i] = pars.getZp().newElement(BigInteger.valueOf(i + 1)).getImmutable();
        }
        for (int i = 0; i < n; i++) {
            result = result.duplicate().mul(l[i].duplicate().powZn(delta(x, pars.getZp().newElement(BigInteger.valueOf(i + 1)), N, pars))).getImmutable();
        }
        result = result.duplicate().mul(pars.getG2().duplicate().powZn(x.duplicate().pow(BigInteger.valueOf(n - 1))));
        return result;
    }
    */

    public static Polynomial newRandomPolynomial(int d, PARS pars)
    {
        return newRandomPolynomial(d, pars.getZp().newRandomElement(), pars);
    }

    public static Polynomial newRandomPolynomial(int d, Element root, PARS pars)
    {
        Polynomial poly = new Polynomial(pars.getZp().newZeroElement().getImmutable(), 0, pars);
        for (int i = 1; i < d; ++i)
            poly = poly.plus(new Polynomial(pars.getZp().newRandomElement(), i, pars));
        poly = poly.plus(new Polynomial(root, 0, pars));
        return poly;
    }

    public static Element[] intersect(Element[] a, Element[] b)
    {
        List<Element> result = new ArrayList<>();
        for (Element a0 : a)
            for (Element b0 : b)
                if (a0.isEqual(b0))
                    result.add(a0);
        Element[] intersection = new Element[result.size()];
        for (int i = 0; i < intersection.length; ++i)
            intersection[i] = result.get(i);
        return intersection;
    }

    public static byte[] byteMergerAll(byte[]... values)
    {
        int length_byte = 0;
        for (int i = 0; i < values.length; ++i)
            length_byte += values[i].length;
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; ++i)
        {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }
}