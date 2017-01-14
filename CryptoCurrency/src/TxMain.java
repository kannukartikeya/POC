// Also Here https://gist.github.com/KKostya/922b944ff1ae337ebf68b92bb4a96ab8
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.SignatureException;

public class TxMain {
	public static void main(String[] args) 
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException 
	{		
		// This generates keypairs
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		// This hashes stuff
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		// This creates signatures
		Signature sig = Signature.getInstance("SHA256withRSA");
		
		Signature sig_groofy = Signature.getInstance("SHA256withRSA");

		
		// Scroodge generates a key pair
		keyGen.initialize(512); 
		KeyPair scroodge  = keyGen.generateKeyPair();
		
		// Creates genesis transaction
		Transaction genesis = new Transaction();
		genesis.addOutput(100, scroodge.getPublic(),new String("Scroodge"));
		
		//Hashes it
		genesis.setHash(md.digest(genesis.getRawTx()));
		
		System.out.println("Hash of genesis " + genesis.getHash());
		
		// Adds it to the pool
		UTXOPool pool = new UTXOPool();
		UTXO utxo = new UTXO(genesis.getHash(), 0);
		pool.addUTXO(utxo, genesis.getOutput(0));
	
		
		// Goofy creates his pair
		keyGen.initialize(512);
		KeyPair goofy    = keyGen.generateKeyPair();
		
		//Scroodge makes a transaction to Goofy
		Transaction send = new Transaction();
		send.addInput(genesis.getHash(), 0);
		send.addOutput(40, goofy.getPublic(),new String("Goofy"));
		System.out.println("Public key of scroodge before signing " + scroodge.getPublic());
		send.addOutput(50, scroodge.getPublic(),new String("Scroodge"));	
		
		// Signs the input with his private key
		sig.initSign(scroodge.getPrivate());
		System.out.println("Message of scroodge before signing " + send.getRawDataToSign(0));
		sig.update(send.getRawDataToSign(0));
		send.addSignature(sig.sign(), 0);
		
		// Hashes 
		send.setHash(md.digest(send.getRawTx()));		
		
		//Adds the output transactions in the pool
		UTXO send_utxo1 = new UTXO(send.getHash(), 0);
		UTXO send_utxo2 = new UTXO(send.getHash(), 1);
		pool.addUTXO(send_utxo1, send.getOutput(0));
		pool.addUTXO(send_utxo2, send.getOutput(1));
		
/*		TxHandler handler = new TxHandler(pool);
		handler.isValidTx(send);*/
		
		//goofy pay back to scroodge 
		Transaction groofy_txn = new Transaction();
		System.out.println("\n Hash of send " + send.getHash());
		groofy_txn.addInput(send.getHash(), 0);
		//send.addOutput(50, goofy.getPublic());
		System.out.println("Public key of goofy before signing " + goofy.getPublic());
		groofy_txn.addOutput(10, goofy.getPublic(), new String("Goofy"));
		
		groofy_txn.addOutput(30, scroodge.getPublic(),new String("Scroodge"));
	
		// Signs the input with his private key
		sig_groofy.initSign(goofy.getPrivate());
		System.out.println("Message before signing " + groofy_txn.getRawDataToSign(0));
		sig_groofy.update(groofy_txn.getRawDataToSign(0));
		groofy_txn.addSignature(sig_groofy.sign(), 0);
						
		groofy_txn.setHash(md.digest(groofy_txn.getRawTx()));
		
		//Adds the output transactions in the pool
		UTXO send_utxog1 = new UTXO(groofy_txn.getHash(), 0);
		UTXO send_utxog2 = new UTXO(groofy_txn.getHash(), 1);
		pool.addUTXO(send_utxog1, groofy_txn.getOutput(0));
		pool.addUTXO(send_utxog2, groofy_txn.getOutput(1));

	/*	TxHandler handler = new TxHandler(pool);
		handler.isValidTx(groofy_txn);
		*/
	
		// kartik creates his pair and goofy double spend to scroodge back and kartik as well
		keyGen.initialize(512);
		KeyPair kartik    = keyGen.generateKeyPair();
		Transaction groofy_txn2 = new Transaction();
		groofy_txn2.addInput(groofy_txn.getHash(), 0);	
		groofy_txn2.addOutput(10, kartik.getPublic(),new String("Kartik"));
		groofy_txn2.addOutput(0, goofy.getPublic(),new String("Goofy"));
		
		// Signs the input with his private ke1
		sig.initSign(goofy.getPrivate());
		sig.update(groofy_txn2.getRawDataToSign(0));
		groofy_txn2.addSignature(sig.sign(), 0);
				
				// Hashes 
		groofy_txn2.setHash(md.digest(groofy_txn2.getRawTx()));
		
		
		TxHandler handler = new TxHandler(pool);
		Transaction[] randoTxn = new Transaction[] {send,groofy_txn,groofy_txn2};
		
		Transaction[] validTxs = handler.handleTxs(randoTxn);
		for(int i=0;i<validTxs.length;i++)
		{
			Transaction valid = validTxs[i];
			System.out.println("valid txn is" + valid.hashCode());
			
		}
			
    }
}