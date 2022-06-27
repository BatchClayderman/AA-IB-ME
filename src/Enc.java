import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import java.util.ArrayList;
import java.util.Collections;


public class Enc
{
	public static ArrayList<Element[]> enc(PARS pars, Element[] ID_A, Element[] P_B, Element[][] ek, Element M)
    {
		/* random set S'' */
		ArrayList<Integer> nbs = pars.randNbs(), S = new ArrayList<Integer>(), S_pi_pi = new ArrayList<Integer>(), I = new ArrayList<Integer>();
		for (int i = 0; i < pars.getK(); ++i)
		{
			S.add(nbs.get(i));
			I.add(nbs.get(i));
		}
		Collections.shuffle(nbs);
		for (int i = 0; i < pars.getK(); ++i)
			S_pi_pi.add(nbs.get(i));
		pars.setS(S);
		pars.setS_pi_pi(S_pi_pi);
		
		/* s, si1, si2 */
        Element s = pars.getZp().newRandomElement().getImmutable();
        Element s_i_1 = pars.getZp().newRandomElement().getImmutable();
        Element s_i_2 = pars.getZp().newRandomElement().getImmutable();

        /* d - 1 degree poly */
        Polynomial q = Utils.newRandomPolynomial(pars.getD() - 1, s, pars);
        
        /* sender computing (1) */
        Element K_s = pars.getY1().powZn(s);
        Element K_l = pars.getY2().powZn(s);
        Element C = M.mul(K_s).mul(K_l);
        Element[] C_i_1 = new Element[pars.getK()], C_i_2 = new Element[pars.getK()], C_i_3 = new Element[pars.getK()], C_i_4 = new Element[pars.getK()], C_i_5 = new Element[pars.getK()];
        int walker = 0;
        for (int i : S_pi_pi)
        {
        	C_i_1[walker] = PARS.H(pars.getU(), P_B).mul(pars.getT()[i]).powZn(q.evaluate(ID_A[i]));
        	C_i_2[walker] = pars.getV1().powZn(q.evaluate(ID_A[i]).sub(s_i_1));
        	C_i_3[walker] = pars.getV2().powZn(s_i_1);
        	C_i_4[walker] = pars.getV3().powZn(q.evaluate(ID_A[i]).sub(s_i_2));
        	C_i_5[walker++] = pars.getV4().powZn(s_i_2);
        }
        
        /* sender computing (2) */
        Element[] C_i_6 = new Element[pars.getK()], C_i_7 = new Element[pars.getK()], C_i_8 = new Element[pars.getK()];
        walker = 0;
        for (int i : S)
        {
        	Element zi = pars.getZp().newRandomElement().duplicate(), zi_pi = pars.getZp().newRandomElement().duplicate();
        	C_i_6[walker] = pars.get_g().powZn(zi_pi);
        	
        	/* If you have (unknown) exceptions here, please check your JDK and JPBC version */
        	try // if not in a group
        	{
        		C_i_7[walker] = (ID_A[S.get(2)].mul(pars.get_g().powZn(zi))).powZn(s);
        		C_i_8[walker++] = (ID_A[S.get(1)].powZn(s)).duplicate().mul(PARS.H(pars.getU_pi(), ID_A).mul(pars.getT_pi()[i]).powZn(s.mul(zi)));
        	}
        	catch (Throwable e)
        	{
        		C_i_7[walker] = pars.getZp().newRandomElement().duplicate();
        		C_i_8[walker++] = pars.getZp().newRandomElement().duplicate();
        	}
        }
        
        /* I = S กษ S'' */
        I.retainAll(S_pi_pi);
        ArrayList<Element> I_star = new ArrayList<Element>();
        
        /* if |I| >= d -> randomly select */
        if (I.size() >= pars.getD())
        {
        	Collections.shuffle(I);
        	for (int i = 0; i < pars.getD(); ++i)
        	{
        		Element ele = pars.getZp().newRandomElement().duplicate(); // initial
        		ele.set(I.get(i).intValue());
        		I_star.add(ele);
        	}
        }

        /* if |I| < d -> adds d - |I| random numbers from Zp* */
        else
        	while (I_star.size() < pars.getD())
        		I_star.add(pars.getZp().newRandomElement().getImmutable());
        
        Element[] tmp_array_1 = new Element[S_pi_pi.size()], tmp_array_2 = new Element[I_star.size()]; 
        walker = 0;
        for (Integer ele : S_pi_pi)
        {
        	Element element = pars.getZp().newRandomElement().duplicate(); // initial
        	element.set(ele);
        	tmp_array_1[walker++] = element;
        }
        walker = 0;
        for (Element ele : I_star)
        	tmp_array_2[walker++] = ele;
        
        ArrayList<Element[]> CT = new ArrayList<>();
        CT.add(tmp_array_1);
        CT.add(tmp_array_2);
        CT.add(new Element[] {C});
        CT.add(C_i_1);
        CT.add(C_i_2);
        CT.add(C_i_3);
        CT.add(C_i_4);
        CT.add(C_i_5);
        CT.add(C_i_6);
        CT.add(C_i_7);
        CT.add(C_i_8);
        return CT;
    }
}
