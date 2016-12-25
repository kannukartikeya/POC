import java.util.ArrayList;
import java.util.List;

public class TxHandler {
	UTXOPool utxoPool_current;
	

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
        // IMPLEMENT THIS
    	utxoPool_current = new UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {  	
    	
/*    	for(Transaction.Output op: tx.getOutputs())
    	{
    		ArrayList<UTXO> utxolist = utxoPool_current.getAllUTXO();
    		for(UTXO utxo : utxolist)
    			if(utxo.getTxHash().equals(tx.getHash()))
    			{
    				
    			}
    		
    	}*/
    	
    	for(Transaction.Input ip: tx.getInputs())
    	{
    		System.out.println("prev transaction hash" + ip.prevTxHash);
    		System.out.println("index" + ip.outputIndex);
    		UTXO inpututxo = new UTXO(ip.prevTxHash,ip.outputIndex);
    		Transaction.Output output = utxoPool_current.getTxOutput(inpututxo);
    		System.out.println("Public key used for verification" + output.address);
    		System.out.println("Message used for verification " + tx.getRawDataToSign(0));
    		if(!Crypto.verifySignature(output.address, tx.getRawDataToSign(ip.outputIndex), ip.signature))
    		{
    			System.out.println("Signature mismatch");
    		return false;  		
    		}
    	}
    	
    	for(Transaction.Output op: tx.getOutputs())
    	{
    		if(op.value<0)
    			return false;
    	}
    	
    	double sum_spent_output = 0;
    	
    	for(Transaction.Input ip: tx.getInputs())
    	{
    		UTXO inpututxo = new UTXO(ip.prevTxHash,ip.outputIndex);
    		Transaction.Output output = utxoPool_current.getTxOutput(inpututxo);
    		sum_spent_output += output.value;

    	}
    	
    	double sum_output  =0;
    	
    	for(Transaction.Output op: tx.getOutputs())
    		sum_output+=op.value;
    	
    	if(sum_spent_output<sum_output)
    			return false;
       	
       	return true;
       // IMPLEMENT THIS
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
    	List<Transaction> validTransaction  = new ArrayList<Transaction>();
    	for (int i=0;i<possibleTxs.length;i++)
    	{
    		if(isValidTx(possibleTxs[i]))
    		{
    			validTransaction.add(possibleTxs[i]);
    			//update pool for double spend logic
    		}
    		else
    		{
    			//delete the invalid transactions from the pool
    		}
    	}
    	return validTransaction.toArray(new Transaction[validTransaction.size()]);
    }

}
