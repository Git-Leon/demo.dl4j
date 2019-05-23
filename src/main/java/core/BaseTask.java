package core;

import presentation.IApplicationController;
import javafx.concurrent.Task;

public abstract class BaseTask<T> extends Task<T> {
  protected final IApplicationController applicationController;
  protected String taskName;

  public BaseTask(String taskName, IApplicationController applicationController){
    this.taskName = taskName;
    this.applicationController = applicationController;
  }

  @Override
  protected T call() throws Exception {
    T result = null;
    try{
      System.out.println("Running task: " + taskName);
      result = runTask();
      System.out.println("Task: " + taskName + " done");
    }
    catch (Exception err) {
      System.out.println("Running task: " + taskName + " Error: " + err.getMessage());
    }
    return result;
  }

  protected abstract T runTask();
}
