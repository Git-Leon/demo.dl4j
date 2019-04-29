package presentation;

public interface IView<TPresenter> {
  void setPresenter(TPresenter presenter);
  TPresenter getPresenter();

  void show();
}