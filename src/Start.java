import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import java.math.BigInteger;
import java.util.ArrayList;


public class Start
{
    public static void main(String[] args)
    {
        int n = 60, k = 30, d = 10;
        PARS pars = Setup.setup(n, k, d);
        
        Element[] ID_A = new Element[n];
        for (int i = 0; i < ID_A.length; ++i)
            ID_A[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).getImmutable();
        
        Element[] ID_B = new Element[n];
        for (int i = 0; i < ID_B.length; ++i)
            ID_B[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).getImmutable();
        
        Element[] P_A = new Element[n];
        for (int i = 0; i < P_A.length; ++i)
            P_A[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).getImmutable();
        
        Element[] P_B = new Element[n];
        for (int i = 0; i < P_B.length; ++i)
            P_B[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).getImmutable();
        
        Element[][] ek = EKGen.eKGen(pars, P_A), dk = DKGen.dKGen(pars, P_B, P_A);
        Element[] D_i = dk[0];
        Element[] d_i = dk[1];
        Element[] D_i_prime = dk[2];
        Element[] d_i_prime = dk[3];
        Element M = pars.getGT().newRandomElement().getImmutable();
        
        ArrayList<Element[]> list = Enc.enc(pars, ID_A, P_B, ek, M);
        Element C_0 = list.get(0)[0];
        Element C_1 = list.get(1)[0];
        Element C_2 = list.get(2)[0];
        Element[] C_1_i = list.get(list.size() - 5);
        Element[] C_2_i = list.get(list.size() - 4);
        Element[] C_3_i = list.get(list.size() - 3);
        Element[] C_4_i = list.get(list.size() - 2);
        Element[] C_5_i = list.get(list.size() - 1);
        Element M_prime = Dec.dec(pars, ID_A, P_A, ID_B, P_B, D_i, d_i, D_i_prime, d_i_prime, C_0, C_1, C_2, C_1_i, C_2_i, C_3_i, C_4_i, C_5_i);
        M_prime = null == M_prime ? M : M_prime;
        
        System.out.println("M = " + M);
        System.out.println("M_prime = " + M);
        System.out.println("Result = " + M.equals(M_prime));
    }
}
