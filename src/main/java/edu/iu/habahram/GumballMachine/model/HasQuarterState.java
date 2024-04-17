package edu.iu.habahram.GumballMachine.model;

public class HasQuarterState implements IState {
    private IGumballMachine gumballMachine;

    public HasQuarterState(IGumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public TransitionResult insertQuarter() {
        return new TransitionResult(false, "Quarter already inserted", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult ejectQuarter() {
        gumballMachine.changeTheStateTo(GumballMachineState.NO_QUARTER);
        return new TransitionResult(true, "Quarter returned", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult turnCrank() {
        gumballMachine.changeTheStateTo(GumballMachineState.GUMBALL_SOLD);
        return new TransitionResult(true, "Turned crank", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult dispense() {
        return new TransitionResult(false, "No gumball dispensed", gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public String getTheName() {
        return GumballMachineState.HAS_QUARTER.name();
    }
}

