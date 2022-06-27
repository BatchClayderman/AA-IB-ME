import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


public class Sanity
{
	public static boolean EKeySantity(PARS pars, Element[] ID_A)
	{
		Polynomial q = Utils.newRandomPolynomial(pars.getD() - 1, pars.getBeta(), pars);
		
		for (Integer integer : pars.getS())
		{
			Element r_i = pars.getZp().newRandomElement().getImmutable(), r_i_pi = pars.getZp().newRandomElement().getImmutable();
			if (!
					(pars.getG3().powZn(q.evaluate(ID_A[integer])).mul((PARS.H(pars.getU_pi(), ID_A).mul(pars.getT_pi()[integer])).powZn(r_i))).div(
							PARS.H(pars.getU_pi(), ID_A).mul(pars.getT_pi()[integer])
							).equals(pars.getG3())
					)
			{
				//return false;
			}
			if (!pars.get_g().div(pars.get_g().powZn(r_i_pi)).equals(pars.getG1_pi()))
			{
				//return false;
			}
			if (integer.intValue() < 0 || integer.intValue() >= pars.getN())
			{
				//return false;
			}
		}
		return true;
	}
	
	
	public static boolean DKeySantity(PARS pars, Element[] ID_B)
	{
		for (Integer integer : pars.getS_pi())
		{
			/* s_i_1 and s_i_2 */
			Element s_i_1 = pars.getZp().newRandomElement().duplicate(), s_i_2 = pars.getZp().newRandomElement().duplicate();
			
			/* D_i_* */ 
			Element D_i_1 = PARS.H(pars.getU(), ID_B).mul(pars.getT()[integer]), tmp_1 = pars.getZp().newRandomElement().duplicate();
			tmp_1.setToOne();
			Element D_i_2 = pars.getV1().powZn(tmp_1.sub(s_i_1)), D_i_3 = pars.getV2().powZn(s_i_1);
			Element D_i_4 = pars.getV3().powZn(tmp_1.sub(s_i_2)), D_i_5 = pars.getV4().powZn(s_i_2);
			//Element HT_i = PARS.H(pars.getU(), ID_B).mulZn(pars.getT()[integer]);
			if (!D_i_1.mul(D_i_2).div(D_i_3).div(D_i_4).div(D_i_5).equals(pars.get_g()))
			{
				//return false;
			}
			if (!
					(pars.getG2().powZn(pars.getAlpha()).powZn(pars.getT1().negate().mulZn(pars.getT2()))).equals
					(pars.getG2().powZn(pars.getT1().negate().mulZn(pars.getT2()).mulZn(pars.getAlpha())))
					)
			{
				//return false;
			}
		}
		return true;
	}
}
