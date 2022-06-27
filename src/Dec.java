import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


public class Dec
{
	public static Element dec(PARS pars, Element[] ID_A, Element[] P_A, Element[] ID_B, Element[] P_B, Element[] D_i, Element[] d_i, Element[] D_i_prime, Element[] d_i_prime, Element C_0, Element C_1, Element C_2, Element[] C_1_i, Element[] C_2_i, Element[] C_3_i, Element[] C_4_i, Element[] C_5_i)
    {
        Element[] W_A_prime = Utils.intersect(P_A, ID_A);
        Element[] W_B_prime = Utils.intersect(P_B, ID_B);
        
        /* Judge */
        if (W_A_prime.length >= pars.getD() && W_B_prime.length >= pars.getD())
        {
            Element[] W_A = new Element[pars.getD()];
            System.arraycopy(W_A_prime, 0, W_A, 0, W_A.length);
            Element[] W_B = new Element[pars.getD()];
            System.arraycopy(W_B_prime, 0, W_B, 0, W_B.length);

            Element K_s_prime = pars.getGT().newOneElement().getImmutable();
            
            /* If you have (unknown) exceptions here, please check your JDK and JPBC version */
            for (int i = 0; i < W_B.length; ++i)
                try
            	{
                	K_s_prime = K_s_prime.mul(pars.getPairing().pairing(D_i[i].duplicate(), C_1.duplicate()).div(pars.getPairing().pairing(d_i[i].duplicate(), C_1_i[i].duplicate())).powZn(Utils.delta(pars.getZp().newZeroElement(), W_B[i], W_B, pars))).getImmutable();
                }
            	catch (Throwable e) {}
            
            Element K_l_prime = pars.getGT().newOneElement().getImmutable();
            for (int i = 0; i < W_A.length; ++i)
                try
            	{
                	K_l_prime = K_l_prime.mul(pars.getPairing().pairing(D_i_prime[i].duplicate(), C_1.duplicate()).mul(pars.getPairing().pairing(pars.getPairing().getG1().newElementFromBytes(Utils.byteMergerAll(C_0.toBytes(), C_1.toBytes(), C_1_i[i].toBytes(), C_2_i[i].toBytes(), C_3_i[i].toBytes(), C_4_i[i].toBytes())).duplicate(),C_4_i[i].duplicate()).duplicate().mul(pars.getPairing().pairing(C_3_i[i].duplicate(), C_2_i[i].duplicate()).duplicate())).div(pars.getPairing().pairing(d_i_prime[i].duplicate(), C_2_i[i].duplicate()).duplicate().mul(pars.getPairing().pairing(C_5_i[i].duplicate(), pars.get_g().duplicate()).duplicate())).powZn(Utils.delta(pars.getZp().newZeroElement(), W_A[i], W_A, pars))).getImmutable();
                }
            	catch (Throwable e) {}
            try
            {
            	return C_0.duplicate().div(K_s_prime.mul(K_l_prime.duplicate())).getImmutable();
            }
            catch (Throwable e)
            {
            	return null;
            }
        }
        else
            return null;
    }
}