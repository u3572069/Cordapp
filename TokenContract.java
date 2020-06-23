package bootcamp;

import examples.ArtContract;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/examples/ArtContract.java for an example. */
public class TokenContract implements Contract {
    public static String ID = "bootcamp.TokenContract";

    public interface Commands extends CommandData {
        class Issue implements Commands { }
        class Transfer implements Commands { }
        class Exit implements Commands { }
    }

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if(tx.getCommands().size() != 1){
            throw new IllegalArgumentException("Transaction must have one command");
        }

        Command command = tx.getCommand(0);
        if(command.getValue() instanceof Commands.Issue ){
        if(tx.getInputs().size() != 0 )
        {
            throw new IllegalArgumentException("Transaction must be zero inputs");
        }
        if(tx.getOutputs().size() != 1)
        {
            throw new IllegalArgumentException("Transaction must be one output");
        }

        if(tx.outputsOfType(TokenState.class).size() != 1){
            throw new IllegalArgumentException("Transaction must be Token State");
        }
        final TokenState tokenStateOutput = tx.outputsOfType(TokenState.class).get(0);
        if(tokenStateOutput.getAmount() <= 0)
        {
            throw new IllegalArgumentException("Output amount must be greater than 0");
        }
        final List<PublicKey> requiredSigners = command.getSigners();
        if (!requiredSigners.contains(tokenStateOutput.getIssuer().getOwningKey()))
            throw new IllegalArgumentException("Transaction should has issuer signature");
        }
        else if(command.getValue() instanceof Commands.Transfer) {
            //Do transfer here
        }else {
            throw new IllegalArgumentException("Unrecognized command");
        }
    }


}