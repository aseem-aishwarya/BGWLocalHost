/**
 * 
 */
package opennlp;

import java.io.*;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 * @author Administrator
 *
 */
public class sentimentalAnalysisIntf implements sentimentAnalysis {
	static DoccatModel model;
	static boolean isNot = false;

	/* (non-Javadoc)
	 * @see opennlp.sentimentAnalysis#checkPolarity()
	 */
	public String checkPolarity(String tweet) {
		// TODO Auto-generated method stub
		InputStream dataIn = null;
		try {
			dataIn = new FileInputStream("inputs.txt");
			ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			System.out.println("lineStream"+lineStream);
			ObjectStream sampleStream = new DocumentSampleStream(lineStream);
			System.out.println("sampleStream"+sampleStream);
			// Specifies the minimum number of times a feature must be seen
			int cutoff = 1;
			int trainingIterations = 1;
			model = DocumentCategorizerME.train("en", sampleStream, cutoff,
					trainingIterations);
			System.out.println("<MOD>"+model);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String result= "";
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(tweet);
		System.out.println("tweet"+tweet);
		for (int i=0; i<outcomes.length;i++){
			System.out.println("outcomes"+outcomes[i]);
		}
		String category = myCategorizer.getBestCategory(outcomes);
		System.out.println("category"+category);
		/*if(isNeutral){
			System.out.println("The tweet is neutral :| ");
			result="Neutral";
		}else*/ 
		if(category.equalsIgnoreCase("2")){
			System.out.println("The tweet is neutral :|");
			result="Neutral";
		}else if ((category.equalsIgnoreCase("1") && !isNot)||(!category.equalsIgnoreCase("1") && isNot)) {
			System.out.println("The tweet is positive :) ");
			result="Positive";
		} else {
			System.out.println("The tweet is negative :( ");
			result="Negative";
		}
		return result;

	}

	@Override
	public String formatInput(String input) throws Exception {
		// TODO Auto-generated method stub
		while(input.lastIndexOf(".")==input.length()-1 || input.lastIndexOf("!")==input.length()-1){
			input = input.substring(0, input.length()-1);
		}
		BufferedReader negative = new BufferedReader(new FileReader("negativeConjunctions.txt"));
		BufferedReader nots = new BufferedReader(new FileReader("negations.txt"));
		String line;
		String[] formattedInput;
		String negate;
		try{
		if(input.contains(".")){
			formattedInput = input.split("\\.");
			input = formattedInput[formattedInput.length-1];
		}
		System.out.println("input2-"+input);
		while((line = negative.readLine()) != null){
			if((input.toUpperCase()).contains(line.toUpperCase())){
				System.out.println("line"+line);
				formattedInput= input.split(line);
				System.out.println("formattedInput"+formattedInput.length+", "+formattedInput[formattedInput.length-1]);
				input = formattedInput[formattedInput.length-1];
			}
		}
		System.out.println("input3-"+input);
		while((negate = nots.readLine()) != null){
			if((input.toUpperCase()).contains(negate.toUpperCase())){
				input= input.replaceAll(negate, "");
				isNot=true;
			}
		}
		System.out.println("input-"+input);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			negative.close();
			nots.close();
		}
		return input;
	}

}
