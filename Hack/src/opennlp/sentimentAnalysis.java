/**
 * 
 */
package opennlp;

/**
 * @author Administrator
 *
 */
public interface sentimentAnalysis {

	public abstract String checkPolarity(String input);
	
	public abstract String formatInput(String input) throws Exception;

}
