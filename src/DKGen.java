import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import java.util.ArrayList;


public class DKGen
{	
	public static Element[][] dKGen(PARS pars, Element[] ID_B, Element[] msk)
	{
        /* ki1 ki2 */
        Element[] k1 = new Element[pars.getN()], k2 = new Element[pars.getN()];
        for (int i = 0; i < pars.getN(); ++i)
        {
        	k1[i] = pars.getZp().newRandomElement().getImmutable();
        	k2[i] = pars.getZp().newRandomElement().getImmutable();
        }
        
        /* compute dk_ID_B */
        Element[][] dk_ID_B = new Element[pars.getN()][5];
        for (int i = 0; i < pars.getN(); ++i)
        {
        	dk_ID_B[i][0] = pars.get_g().powZn(k1[i].mul(pars.getT1().mul(pars.getT2())).add(k2[i].mul(pars.getT3().mul(pars.getT4()))));
	        dk_ID_B[i][1] = pars.getG2().powZn(pars.getAlpha().negate().mul(pars.getT2())).mul(PARS.H(pars.getU(), ID_B).mul(pars.getT()[1]).powZn(k1[i].mul(pars.getT2())));
	        dk_ID_B[i][2] = pars.get_g().powZn(pars.getAlpha().mul(pars.getT1())).mul(PARS.H(pars.getU(), ID_B).mul(pars.getT()[2].powZn(k1[i].mul(pars.getT1()))));
	        dk_ID_B[i][3] = PARS.H(pars.getU(), ID_B).mul(pars.getT()[2]).powZn(k2[i].mul(pars.getT4()));
	        dk_ID_B[i][4] = PARS.H(pars.getU(), ID_B).mul(pars.getT()[3]).powZn(k2[i].mul(pars.getT3()));
        }
        
        /* random set */
        ArrayList<Integer> nbs = pars.randNbs();
		
		/* k_out_of_n OT */
		ArrayList<Integer> S_pi = new ArrayList<Integer>();
		for (int i = 0; i < pars.getK(); ++i)
			S_pi.add(nbs.get(i));
		pars.setS_pi(S_pi);
        
        /* finally */
		Element[][] eRet = new Element[pars.getK()][5];
		int walker = 0;
		for (Integer i : S_pi)
		{
			for (int j = 0; j < 5; ++j)
				eRet[walker][j] = dk_ID_B[i][j];
			++walker;
		}
		
		return eRet;
	}
}