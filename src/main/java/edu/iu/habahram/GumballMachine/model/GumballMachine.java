package edu.iu.habahram.GumballMachine.model;

public class GumballMachine implements IGumballMachine {
    final String SOLD_OUT = GumballMachineState.OUT_OF_GUMBALLS.name();
    final String NO_QUARTER = GumballMachineState.NO_QUARTER.name();
    final String HAS_QUARTER = GumballMachineState.HAS_QUARTER.name();
    final String SOLD = GumballMachineState.GUMBALL_SOLD.name();
    private String id;
    String state = SOLD_OUT;
    int count = 0;

    public GumballMachine(String id, String state, int count) {
        this.id = id;
        this.state = state;
        this.count = count;
    }

    @Override
    public TransitionResult insertQuarter() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "You can't insert another quarter";
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            state = HAS_QUARTER;
            message = "You inserted a quarter";
            succeeded = true;
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "You can't insert a quarter, the machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Please wait, we're already giving you a gumball";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult ejectQuarter() {
        boolean succeeded = false;
        String message = "";

        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            state = NO_QUARTER;
            message = "Quarter returned";
            succeeded = true;
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            message = "No quarter to return";
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "No quarter to return";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Wait, dispensing a gumball";
        }

        return new TransitionResult(succeeded, message, state, count);
    }
    @Override
    public TransitionResult turnCrank() {
        boolean succeeded = false;
        String message = "";

        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            if (count > 0) {
                releaseBall();  // Dispense the gumball
                message = "Enjoy your gumball!";
                succeeded = true;
                if (count > 0) {
                    state = NO_QUARTER;  // Change state back to NO_QUARTER to allow next transaction
                } else {
                    state = SOLD_OUT;  // No more gumballs left, change state to SOLD_OUT
                }
            } else {
                state = SOLD_OUT;
                message = "No gumballs left";
            }
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            message = "You need to insert a quarter first";
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "Machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Already dispensing a gumball";
        }

        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public void changeTheStateTo(GumballMachineState name) {
        state = name.name();
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public void setCount(Integer integer) {
        this.count = integer;
    }

    @Override
    public String getTheStateName() {
        return state;
    }

    @Override
    public void releaseBall() {
        if (count > 0) {
            count--;
        }
    }

    @Override
    public void refill(int count) {
        this.count += count; // Increment gumball count
        if (this.state.equals(SOLD_OUT) && count > 0) {
            this.state = NO_QUARTER;
        }
    }


}
