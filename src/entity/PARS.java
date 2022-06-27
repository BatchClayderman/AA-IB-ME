package entity;
import java.util.ArrayList;
import java.util.Collections;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;


public class PARS
{
	private int n, k, d; // n, k, d
	private ArrayList<Integer> S, S_pi, S_pi_pi;
	private Element g, g1, g1_pi, g2, g3; // The elements g, g1, g2, and g3
    private Element alpha, beta; // $\alpha$ and $\beta$
    private Element t1, t2, t3, t4; // random numbers from t1 to t4
    private Element[] T, T_pi, u, u_pi;  // four random vectors
    private Element Y1, Y2; // Y1 and Y2
    private Element v1, v2, v3, v4; // four $v$ vars
    private Element[] mpk, msk; // public key and master key 
    private Element[] ID_A, ID_B, ID; // IDs
    private Pairing pairing;
    private Field<Element> G; // Group
    private Field<Element> GT; // Paired Group
    private Field<Element> Zp; // Zp_star
    private ArrayList<Integer> nbs; // just a buffer for shuffling
    
    
    public void setN(int n)
    {
    	this.n = n;
    	return;
    }
    public int getN()
    {
    	return this.n;
    }
    
    public void setK(int k)
    {
    	this.k = k;
    	return;
    }
    public int getK()
    {
    	return this.k;
    }
    
    public void setD(int d)
    {
    	this.d = d;
    	return;
    }
    public int getD()
    {
    	return this.d;
    }
    
    public void setS(ArrayList<Integer> S)
    {
    	this.S = S;
    	return;
    }
    public ArrayList<Integer> getS()
    {
    	return this.S;
    }
    
    public void setS_pi(ArrayList<Integer> S_pi)
    {
    	this.S_pi = S_pi;
    	return;
    }
    public ArrayList<Integer> getS_pi()
    {
    	return this.S_pi;
    }
    
    public void setS_pi_pi(ArrayList<Integer> S_pi_pi)
    {
    	this.S_pi_pi = S_pi_pi;
    	return;
    }
    public ArrayList<Integer> getS_pi_pi()
    {
    	return this.S_pi_pi;
    }
    
    public void set_g(Element g)
    {
        this.g = g;
        return;
    }
    public Element get_g()
    {
        return this.g;
    }
    
    public void setG1(Element g1)
    {
        this.g1 = g1;
        return;
    }
    public Element getG1()
    {
        return this.g1;
    }
    
    public void setG1_pi(Element g1_pi)
    {
        this.g1_pi = g1_pi;
        return;
    }
    public Element getG1_pi()
    {
        return this.g1_pi;
    }
    
    public void setG2(Element g2)
    {
        this.g2 = g2;
        return;
    }
    public Element getG2()
    {
        return this.g2;
    }
    
    public void setG3(Element g3)
    {
        this.g3 = g3;
        return;
    }
    public Element getG3()
    {
        return this.g3;
    }
    
    public void setT1(Element t1)
    {
        this.t1 = t1;
        return;
    }
    public Element getT1()
    {
        return this.t1;
    }
    
    public void setT2(Element t2)
    {
        this.t2 = t2;
        return;
    }
    public Element getT2()
    {
        return this.t2;
    }
    
    public void setT3(Element t3)
    {
        this.t3 = t3;
        return;
    }
    public Element getT3()
    {
        return this.t3;
    }
    
    public void setT4(Element t4)
    {
        this.t4 = t4;
        return;
    }
    public Element getT4()
    {
        return this.t4;
    }
    
    public void setTn(Element[] t)
    {
    	if (t.length <= 0)
    		return;
        switch (t.length)
        {
        default:
        	this.t4 = t[3];
        case 3:
        	this.t3 = t[2];
        case 2:
        	this.t2 = t[1];
        case 1:
        	this.t1 = t[0];
        }
        return;
    }
    public Element[] getTn()
    {
        return new Element[] {this.t1, this.t2, this.t3, this.t4};
    }
    
    public void setT(Element[] T)
    {
    	this.T = T;
    	return;
    }
    public Element[] getT()
    {
    	return this.T;
    }
    
    public void setT_pi(Element[] T_pi)
    {
    	this.T_pi = T_pi;
    	return;
    }
    public Element[] getT_pi()
    {
    	return this.T_pi;
    }
    
    public void setU(Element[] u)
    {
    	this.u = u;
    	return;
    }
    public Element[] getU()
    {
    	return this.u;
    }
    
    public void setU_pi(Element[] u_pi)
    {
    	this.u_pi = u_pi;
    	return;
    }
    public Element[] getU_pi()
    {
    	return this.u_pi;
    }
    
    public void setY1(Element Y1)
    {
        this.Y1 = Y1;
        return;
    }
    public Element getY1()
    {
        return this.Y1;
    }
    
    public void setY2(Element Y2)
    {
        this.Y2 = Y2;
        return;
    }
    public Element getY2()
    {
        return this.Y2;
    }
    
    public void setV1(Element v1)
    {
        this.v1 = v1;
        return;
    }
    public Element getV1()
    {
        return this.v1;
    }
    
    public void setV2(Element v2)
    {
        this.v2 = v2;
        return;
    }
    public Element getV2()
    {
        return this.v2;
    }
    
    public void setV3(Element v3)
    {
        this.v3 = v3;
        return;
    }
    public Element getV3()
    {
        return this.v3;
    }
    
    public void setV4(Element v4)
    {
        this.v4 = v4;
        return;
    }
    public Element getV4()
    {
        return this.v4;
    }
    
    public void setVn(Element[] v)
    {
    	if (v.length <= 0)
    		return;
        switch (v.length)
        {
        default:
        	this.v4 = v[3];
        case 3:
        	this.v3 = v[2];
        case 2:
        	this.v2 = v[1];
        case 1:
        	this.v1 = v[0];
        }
        return;
    }
    public Element[] getVn()
    {
        return new Element[] {this.v1, this.v2, this.v3, this.v4};
    }
    
    public void setMpk(Element[] mpk)
    {
    	this.mpk = mpk;
    	return;
    }
    public Element[] getMpk()
    {
    	return this.mpk;
    }
    
    public void setMsk(Element[] msk)
    {
    	this.msk = msk;
    	return;
    }
    public Element[] getMsk()
    {
    	return this.msk;
    }
    
    public void setAlpha(Element alpha)
    {
        this.alpha = alpha;
        return;
    }
    public Element getAlpha()
    {
        return this.alpha;
    }
    
    public void setBeta(Element beta)
    {
        this.beta = beta;
        return;
    }
    public Element getBeta()
    {
        return this.beta;
    }
    
    public void setPairing(Pairing pairing)
    {
        this.pairing = pairing;
        return;
    }
    public Pairing getPairing()
    {
        return this.pairing;
    }
    
    public void setID_A(Element[] ID_A)
    {
    	this.ID_A = ID_A;
    	return;
    }
    public Element[] getID_A()
    {
    	return this.ID_A;
    }
    
    public void setID_B(Element[] ID_B)
    {
    	this.ID_B = ID_B;
    	return;
    }
    public Element[] getID_B()
    {
    	return this.ID_B;
    }
    
    public void setID(Element[] ID)
    {
    	this.ID = ID;
    	return;
    }
    public Element[] getID()
    {
    	return this.ID;
    }
    
    public void setG(Field<Element> G)
    {
    	this.G = G;
    	return;
    }
    public Field<Element> getG()
    {
        return this.G;
    }

    public void setGT(Field<Element> GT)
    {
        this.GT = GT;
        return;
    }
    public Field<Element> getGT()
    {
        return this.GT;
    }
    
    public void setZp(Field<Element> Zp)
    {
        this.Zp = Zp;
        return;
    }
    public Field<Element> getZp()
    {
        return this.Zp;
    }
    
    public Element H1(Element element) // Assume H1 do nothing or you can use other hash functions
    {
    	return element;
    }
    
    public static Element H(Element[] u, Element[] ID) // len(ID) = n, len(u) = n + 1
    {
    	Element eRet = u[0].getImmutable();
    	for (int i = 1; i <= ID.length; ++i)
    		eRet = eRet.mul(u[i].powZn(ID[i - 1]));
    	return eRet;
    }
    
    public Element H_pi()
    {
    	Element eRet = this.u_pi[0].getImmutable();
    	for (int i = 1; i <= this.n; ++i)
    		eRet = eRet.mul(this.u_pi[i].powZn(this.ID[i - 1]));
    	return eRet;
    }
    
    public void initNbs()
    {
    	this.nbs = new ArrayList<Integer>();
    	for (int i = 0; i < this.n; ++i)
			this.nbs.add((Integer)(i));
    	return;
    }
    
    public ArrayList<Integer> randNbs()
    {
    	Collections.shuffle(this.nbs);
    	return this.nbs;
    }
    
    public ArrayList<Integer> getNbs()
    {
    	return this.nbs;
    }
    
    public static void breakpoint(String string)
    {
    	System.out.println("Breakpoint: " + string);
    }
    
    public static void breakpoint()
    {
    	System.out.println("[Breakpoint]");
    }
}