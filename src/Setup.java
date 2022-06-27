import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


public class Setup
{
    public static PARS setup(int n, int k, int d)
	{
		/* input the security parameter */
		int rBits = 160;
	    int qBits = 512;
		
		/* the authority selects a bilinear map $e$: $G \times G \rightarrow G_T$ */
		TypeACurveGenerator pg = new TypeACurveGenerator(rBits, qBits);
	    PairingParameters pairingParameters = pg.generate();
	    Pairing pairing = PairingFactory.getPairing(pairingParameters);
		PARS pars = new PARS();
		pars.setN(n);
		pars.setK(k);
		pars.setD(d);
	    pars.setPairing(pairing);
	    pars.setG(pairing.getG1());
	    pars.setGT(pairing.getGT());
	    pars.setZp(pairing.getZr());
	    pars.initNbs(); // initial a random set for shuffle
	    
	    /* six random nmbers */
	    pars.setAlpha(pars.getZp().newRandomElement().duplicate().getImmutable());
	    pars.setBeta(pars.getZp().newRandomElement().duplicate().getImmutable());
	    pars.setT1(pars.getZp().newRandomElement().duplicate().getImmutable());
	    pars.setT2(pars.getZp().newRandomElement().duplicate().getImmutable());
	    pars.setT3(pars.getZp().newRandomElement().duplicate().getImmutable());
	    pars.setT4(pars.getZp().newRandomElement().duplicate().getImmutable());
	    
	    /* two random elements: g2 and g3 */
	    pars.setG2(pars.getG().newRandomElement().duplicate().getImmutable());
	    pars.setG3(pars.getG().newRandomElement().duplicate().getImmutable());
	    
	    /* four random vectors */
	    Element[] T = new Element[n + 1];
	    Element[] T_pi = new Element[n + 1];
	    Element[] u = new Element[n + 1];
	    Element[] u_pi = new Element[n + 1];
	    for (int i = 0; i <= n; ++i)
	    {
	    	T[i] = pars.getG().newRandomElement().duplicate().getImmutable();
	    	T_pi[i] = pars.getG().newRandomElement().duplicate().getImmutable();
	    	u[i] = pars.getG().newRandomElement().duplicate().getImmutable();
	    	u_pi[i] = pars.getG().newRandomElement().duplicate().getImmutable();
	    }
	    pars.setT(T);
	    pars.setT_pi(T_pi);
	    pars.setU(u);
	    pars.setU_pi(u_pi);
	    
	    /* hash function H1 */
	    // written in pars
	    
	    /* computes */
	    pars.set_g(pars.getG().newRandomElement().duplicate().getImmutable());
	    pars.setG1(pars.get_g().powZn(pars.getAlpha()));
	    pars.setG1_pi(pars.get_g().powZn(pars.getBeta()));
	    pars.setY1(pairing.pairing(pars.getG1(), pars.getG2()).duplicate().powZn(pars.getT1().mul(pars.getT2())).getImmutable());
	    pars.setY2(pars.getY1());//pars.setY2(pairing.pairing(pars.getG3(), pars.get_g()).duplicate().powZn(pars.getBeta()).getImmutable());
	    pars.setV1(pars.get_g().powZn(pars.getT1()));
	    pars.setV2(pars.get_g().powZn(pars.getT2()));
	    pars.setV3(pars.get_g().powZn(pars.getT3()));
	    pars.setV4(pars.get_g().powZn(pars.getT4()));
	    
	    /* identity */
	    Element[] ID = new Element[n];
	    for (int i = 0; i < n - 1; ++i)
	    {
	    	ID[i] = pars.getG().newRandomElement(); // avoid nullptr
	    	ID[i].set(Math.random() * 2 >= 1 ? 1 : 0);
	    }
	    pars.setID(ID);
	    
	    /* the two keys */
	    Element[] mpk = new Element[n << 2 + 11];
	    mpk[0] = pars.getG1();
	    mpk[1] = pars.getG1_pi();
	    mpk[2] = pars.getG2();
	    mpk[3] = pars.getG3();
	    mpk[4] = pars.getY1();
	    mpk[5] = pars.getY2();
	    mpk[6] = pars.getV1();
	    mpk[7] = pars.getV2();
	    mpk[8] = pars.getV3();
	    mpk[9] = pars.getV4();
	    int pointer = 10;
	    for (int i = 0; i <= n; ++i)
	    	mpk[pointer++] = u[i];
	    for (int i = 0; i <= n; ++i)
	    	mpk[pointer++] = T[i];
	    for (int i = 0; i <= n; ++i)
	    	mpk[pointer++] = u_pi[i];
	    for (int i = 0; i <= n; ++i)
	    	mpk[pointer++] = T_pi[i];
	    mpk[pointer] = pars.H1(pars.getG().newRandomElement().getImmutable());
	    pars.setMpk(mpk);
	    
	    Element[] msk = new Element[6];
	    msk[0] = pars.getG2().powZn(pars.getAlpha());
	    msk[1] = pars.getBeta();
	    msk[2] = pars.getT1();
	    msk[3] = pars.getT2();
	    msk[4] = pars.getT3();
	    msk[5] = pars.getT4();
	    pars.setMsk(msk);
	    
	    return pars;
	}
}