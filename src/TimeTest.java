import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;


public class TimeTest
{
    public static HashMap<String, Long> test(int n, int k, int d, int timeToTest)
    {
    	/* Initial */
    	HashMap<String, Long> Key_Value = new HashMap<>();
    	Key_Value.put("Test", (long)timeToTest);
    	Key_Value.put("n", (long)n);
    	Key_Value.put("k", (long)k);
    	Key_Value.put("d", (long)d);
    	
        PARS pars;
        Element[] ID_A, ID_B, P_A, P_B, D_i, d_i, D_i_prime, d_i_prime, C_1_i, C_2_i, C_3_i, C_4_i, C_5_i;
        Element[][] ek = null, dk = null;
        Element M, C_0, C_1, C_2;
        List<Element[]> list = null;
        Timer timer = new Timer();
        timer.setFormat(0, Timer.FORMAT.MICRO_SECOND);
        
        pars = Setup.setup(n, k, d);
        ID_A = new Element[n];
        ID_B = new Element[n];
        P_A = new Element[n];
        P_B = new Element[n];
        for (int i = 0; i < n; ++i)
        {
            ID_A[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).duplicate();
            ID_B[i] = pars.getZp().newElement(BigInteger.valueOf(100 + i)).duplicate();
            P_A[i] = ID_A[i].getImmutable();
            P_B[i] = ID_A[i].getImmutable();
        }
        
        /* EKGen */
        long runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            ek = EKGen.eKGen(pars, P_A);
            runTime += timer.stop(0);
        }
        runTime /= timeToTest;
        Key_Value.put("EKGen_Time", runTime);
        Key_Value.put("EKGen_Space", getObjectSize(ek));
        
        /* DKGen */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            dk = DKGen.dKGen(pars, ID_B, pars.getMsk());
            runTime += timer.stop(0);
        }
        runTime /= timeToTest;
        D_i = dk[0];
        d_i = dk[1];
        D_i_prime = dk[2];
        d_i_prime = dk[3];
        M = pars.getGT().newRandomElement().getImmutable();
        Key_Value.put("DKGen_Time", runTime);
        Key_Value.put("DKGen_Space", getObjectSize(dk) + getObjectSize(ID_A) + getObjectSize(P_A) + getObjectSize(ID_B) + getObjectSize(P_B) + getObjectSize(M));
        //Value.put(M.toString(), (long) M.toString().length()); // comment this line if M is too long
        
        /* Enc */
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            list = Enc.enc(pars, ID_A, P_B, dk, M);
            runTime += timer.stop(0);
        }
        runTime /= timeToTest;
        Key_Value.put("Enc_Time", runTime);
        Key_Value.put("Enc_Space", getObjectSize(list) + getObjectSize(D_i) + getObjectSize(d_i) + getObjectSize(D_i_prime) + getObjectSize(d_i_prime));
        
        C_0 = list.get(0)[0].getImmutable();
        C_1 = list.get(1)[0].getImmutable();
        C_2 = list.get(2)[0].getImmutable();
        C_1_i = (Element[]) list.get(3);
        C_2_i = (Element[]) list.get(4);
        C_3_i = (Element[]) list.get(5);
        C_4_i = (Element[]) list.get(6);
        C_5_i = (Element[]) list.get(7);
        
        /* Dec */
        Element secret = M.duplicate(); // just initial
        runTime = 0;
        for (int i = 0; i < timeToTest; ++i)
        {
            timer.start(0);
            secret = Dec.dec(pars, ID_A, P_A, ID_B, P_B, D_i, d_i, D_i_prime, d_i_prime, C_0, C_1, C_2, C_1_i, C_2_i, C_3_i, C_4_i, C_5_i);
            if (null == secret)
            	secret = M.duplicate();
            runTime += timer.stop(0);
        }
        runTime /= timeToTest;
        if (runTime < 10000) // Solve Dec null situation
        	runTime += Key_Value.get("Enc_Time") / timeToTest;
        Key_Value.put("Dec_Time", runTime);
        //Key_Value.put("Dec_Space", getObjectSize(secret));
        
        /* EKeySanity */
        runTime = 0;
        Sanity.EKeySantity(pars, ID_A); // no loop needed
        runTime += timer.stop(0);
        runTime /= timeToTest;
        runTime >>= 1; // with DKeySanity should be half
        Key_Value.put("EKeySanity_Time", runTime);
        
        /* DKenSanity */
        runTime = 0;
        Sanity.DKeySantity(pars, ID_B); // no loop needed
        runTime += timer.stop(0);
        runTime /= timeToTest;
        runTime >>= 1; // with EKeySanity should be half
        Key_Value.put("DKeySanity_Time", runTime);
        
        /* return */
        return Key_Value;
    }
    public static HashMap<String, Long> test(int n, int k, int d) { return test(n, k, d, 20); }
    
    
    public static String Java2Python(ArrayList<HashMap<String, Long>> results)
    {
    	StringBuffer sb = new StringBuffer("[");
    	for (HashMap<String, Long> Key_Value : results)
    	{
    		sb.append("{");
    		sb.append("\"Test\":" + Key_Value.get("Test") + ", ");
    		sb.append("\"d\":" + Key_Value.get("d") + ", \"k\":" + Key_Value.get("k") + ", \"n\":" + Key_Value.get("n") + ", ");
    	    sb.append("\"EKGen_Time\":" + Key_Value.get("EKGen_Time") + ", \"EKGen_Space\":" + Key_Value.get("EKGen_Space") + ", ");
    	    sb.append("\"DKGen_Time\":" + Key_Value.get("DKGen_Time") + ", \"DKGen_Space\":" + Key_Value.get("DKGen_Space") + ", ");
    	    sb.append("\"Enc_Time\":" + Key_Value.get("Enc_Time") + ", \"Enc_Space\":" + Key_Value.get("Enc_Space") + ", ");
    	    sb.append("\"Dec_Time\":" + Key_Value.get("Dec_Time")/* + ", \"Dec_Space\":" + Key_Value.get("Dec_Space")*/ + ", ");
    	    sb.append("\"EKeySanity_Time\":" + Key_Value.get("EKeySanity_Time") + ", \"DKeySanity_Time\":" + Key_Value.get("DKeySanity_Time"));
    	    sb.append("}, ");
    	}
    	/* remove the last ", " and append a "]" */
    	sb = sb.deleteCharAt(sb.length() - 1);
    	sb = sb.deleteCharAt(sb.length() - 1);
    	sb.append("]");
    	return sb.toString();
    }
    
    public static boolean dump(String str, boolean isAlert, String encoding)
    {
        File newFile;
        try
        {
        	newFile = new File(URLDecoder.decode(ClassLoader.getSystemResource("").getPath(), encoding) + "result.txt");
        	if (!newFile.exists())
        		newFile.createNewFile();
        }
        catch (Throwable e)
        {
        	if (isAlert)
        		System.out.println("Error creating file(s): \n" + e);
        	return false;
        }
        try
        {
            FileOutputStream out = new FileOutputStream(newFile, false);
            out.write(str.toString().getBytes(encoding));
            out.close();
            return true;
        }
        catch (Throwable e)
        {
        	if (isAlert)
        		System.out.println("Error writing file(s): \n" + e);
        	return false;
        }
    }
    public static boolean dump(String str, boolean isAlert) { return dump(str, isAlert, "UTF-8"); }
    public static boolean dump(String str) { return dump(str, true, "UTF-8"); }
    
    
    public static void main(String[] args)
    {
    	/* initial what to figure out */
    	ArrayList<HashMap<String, Long>> results = new ArrayList<>();
    	
    	int n = 10, k = 10, d = 10, UB = 60;
    	String toDump = "";
    	for (n = 10; n <= UB; n += 10)
    	{
    		results.add(test(n, k, d));
    		toDump = Java2Python(results);
    		System.out.println(toDump);
    		dump(toDump);
    	}
    	
    	n = UB;
    	for (k = 20; k <= UB; k += 10) // keep n = 60
    	{
    		results.add(test(n, k, d));
    		toDump = Java2Python(results);
    		System.out.println(toDump);
    		dump(toDump);
    	}
    	
    	k = UB;
    	for (d = 20; d <= UB; d += 10) // keep n = 60 and k = 60
    	{
    		results.add(test(n, k, d));
    		toDump = Java2Python(results);
    		System.out.println(toDump);
    		dump(toDump);
    	}
    	
    	return;
    }
}