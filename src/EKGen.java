import java.util.ArrayList;
import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


public class EKGen
{
    public static Element[][] eKGen(PARS pars, Element[] ID_A)
    {
    	/* q */
        Polynomial q = Utils.newRandomPolynomial(pars.getD() - 1, pars.getBeta(), pars); // $q(0) = \beta$
        
        /* Elmenet ek_ID_A */
        Element[][] eRet = new Element[pars.getK()][2];
        
        /* k-out-of-n OT */
        int[] k_out_of_n = new int[pars.getK()];
        ArrayList<Integer> nbs = pars.randNbs();
        for (int i = 0; i < pars.getK(); ++i)
        	k_out_of_n[i] = nbs.get(i);
        
        /* eRet */
        int walker = 0;
        for (int i : k_out_of_n)
        {
        	Element r_i = pars.getZp().newRandomElement().getImmutable();
        	eRet[walker][0] = pars.getG3().powZn(q.evaluate(ID_A[i])).mul(PARS.H(pars.getU_pi(), ID_A).mul(pars.getT_pi()[i]).powZn(r_i)).getImmutable();
        	eRet[walker++][1] = pars.get_g().powZn(r_i).getImmutable();
        }
        return eRet;
    }
}