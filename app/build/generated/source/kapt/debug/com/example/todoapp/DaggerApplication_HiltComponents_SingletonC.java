// Generated by Dagger (https://dagger.dev).
package com.example.todoapp;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.todoapp.database.Database;
import com.example.todoapp.database.dao.ItemDao;
import com.example.todoapp.di.AppModule;
import com.example.todoapp.di.AppModule_ProvideDatabaseFactory;
import com.example.todoapp.di.AppModule_ProvideItemDaoFactory;
import com.example.todoapp.ui.MainActivity;
import com.example.todoapp.ui.addedit.AddEditFragment;
import com.example.todoapp.ui.addedit.AddEditFragmentViewModel;
import com.example.todoapp.ui.addedit.AddEditFragmentViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.todoapp.ui.addedit.dialogs.InsertErrorDialog;
import com.example.todoapp.ui.items.MainFragment;
import com.example.todoapp.ui.items.MainFragmentViewModel;
import com.example.todoapp.ui.items.MainFragmentViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.todoapp.ui.items.deleteall.DeleteAllCompletedDialog;
import com.example.todoapp.ui.items.deleteall.DeleteAllCompletedViewModel;
import com.example.todoapp.ui.items.deleteall.DeleteAllViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_Lifecycle_Factory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.MemoizedSentinel;
import dagger.internal.Preconditions;
import dagger.internal.SetBuilder;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerApplication_HiltComponents_SingletonC extends Application_HiltComponents.SingletonC {
  private final ApplicationContextModule applicationContextModule;

  private volatile Object database = new MemoizedSentinel();

  private DaggerApplication_HiltComponents_SingletonC(
      ApplicationContextModule applicationContextModuleParam) {
    this.applicationContextModule = applicationContextModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  private Database database() {
    Object local = database;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = database;
        if (local instanceof MemoizedSentinel) {
          local = AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideApplicationFactory.provideApplication(applicationContextModule));
          database = DoubleCheck.reentrantCheck(database, local);
        }
      }
    }
    return (Database) local;
  }

  private ItemDao itemDao() {
    return AppModule_ProvideItemDaoFactory.provideItemDao(database());
  }

  @Override
  public void injectApplication(Application application) {
  }

  @Override
  public ActivityRetainedComponentBuilder retainedComponentBuilder() {
    return new ActivityRetainedCBuilder();
  }

  @Override
  public ServiceComponentBuilder serviceComponentBuilder() {
    return new ServiceCBuilder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder appModule(AppModule appModule) {
      Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public Application_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new DaggerApplication_HiltComponents_SingletonC(applicationContextModule);
    }
  }

  private final class ActivityRetainedCBuilder implements Application_HiltComponents.ActivityRetainedC.Builder {
    @Override
    public Application_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl();
    }
  }

  private final class ActivityRetainedCImpl extends Application_HiltComponents.ActivityRetainedC {
    private volatile Object lifecycle = new MemoizedSentinel();

    private ActivityRetainedCImpl() {

    }

    private Object lifecycle() {
      Object local = lifecycle;
      if (local instanceof MemoizedSentinel) {
        synchronized (local) {
          local = lifecycle;
          if (local instanceof MemoizedSentinel) {
            local = ActivityRetainedComponentManager_Lifecycle_Factory.newInstance();
            lifecycle = DoubleCheck.reentrantCheck(lifecycle, local);
          }
        }
      }
      return (Object) local;
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder();
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return (ActivityRetainedLifecycle) lifecycle();
    }

    private final class ActivityCBuilder implements Application_HiltComponents.ActivityC.Builder {
      private Activity activity;

      @Override
      public ActivityCBuilder activity(Activity activity) {
        this.activity = Preconditions.checkNotNull(activity);
        return this;
      }

      @Override
      public Application_HiltComponents.ActivityC build() {
        Preconditions.checkBuilderRequirement(activity, Activity.class);
        return new ActivityCImpl(activity);
      }
    }

    private final class ActivityCImpl extends Application_HiltComponents.ActivityC {
      private ActivityCImpl(Activity activity) {

      }

      @Override
      public void injectMainActivity(MainActivity mainActivity) {
      }

      @Override
      public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
        return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(ApplicationContextModule_ProvideApplicationFactory.provideApplication(DaggerApplication_HiltComponents_SingletonC.this.applicationContextModule), getViewModelKeys(), new ViewModelCBuilder());
      }

      @Override
      public Set<String> getViewModelKeys() {
        return SetBuilder.<String>newSetBuilder(3).add(AddEditFragmentViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DeleteAllViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(MainFragmentViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
      }

      @Override
      public ViewModelComponentBuilder getViewModelComponentBuilder() {
        return new ViewModelCBuilder();
      }

      @Override
      public FragmentComponentBuilder fragmentComponentBuilder() {
        return new FragmentCBuilder();
      }

      @Override
      public ViewComponentBuilder viewComponentBuilder() {
        return new ViewCBuilder();
      }

      private final class FragmentCBuilder implements Application_HiltComponents.FragmentC.Builder {
        private Fragment fragment;

        @Override
        public FragmentCBuilder fragment(Fragment fragment) {
          this.fragment = Preconditions.checkNotNull(fragment);
          return this;
        }

        @Override
        public Application_HiltComponents.FragmentC build() {
          Preconditions.checkBuilderRequirement(fragment, Fragment.class);
          return new FragmentCI(fragment);
        }
      }

      private final class FragmentCI extends Application_HiltComponents.FragmentC {
        private FragmentCI(Fragment fragment) {

        }

        @Override
        public void injectAddEditFragment(AddEditFragment addEditFragment) {
        }

        @Override
        public void injectInsertErrorDialog(InsertErrorDialog insertErrorDialog) {
        }

        @Override
        public void injectMainFragment(MainFragment mainFragment) {
        }

        @Override
        public void injectDeleteAllDialog(DeleteAllCompletedDialog deleteAllCompletedDialog) {
        }

        @Override
        public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
          return ActivityCImpl.this.getHiltInternalFactoryFactory();
        }

        @Override
        public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
          return new ViewWithFragmentCBuilder();
        }

        private final class ViewWithFragmentCBuilder implements Application_HiltComponents.ViewWithFragmentC.Builder {
          private View view;

          @Override
          public ViewWithFragmentCBuilder view(View view) {
            this.view = Preconditions.checkNotNull(view);
            return this;
          }

          @Override
          public Application_HiltComponents.ViewWithFragmentC build() {
            Preconditions.checkBuilderRequirement(view, View.class);
            return new ViewWithFragmentCI(view);
          }
        }

        private final class ViewWithFragmentCI extends Application_HiltComponents.ViewWithFragmentC {
          private ViewWithFragmentCI(View view) {

          }
        }
      }

      private final class ViewCBuilder implements Application_HiltComponents.ViewC.Builder {
        private View view;

        @Override
        public ViewCBuilder view(View view) {
          this.view = Preconditions.checkNotNull(view);
          return this;
        }

        @Override
        public Application_HiltComponents.ViewC build() {
          Preconditions.checkBuilderRequirement(view, View.class);
          return new ViewCI(view);
        }
      }

      private final class ViewCI extends Application_HiltComponents.ViewC {
        private ViewCI(View view) {

        }
      }
    }

    private final class ViewModelCBuilder implements Application_HiltComponents.ViewModelC.Builder {
      private SavedStateHandle savedStateHandle;

      @Override
      public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
        this.savedStateHandle = Preconditions.checkNotNull(handle);
        return this;
      }

      @Override
      public Application_HiltComponents.ViewModelC build() {
        Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
        return new ViewModelCImpl(savedStateHandle);
      }
    }

    private final class ViewModelCImpl extends Application_HiltComponents.ViewModelC {
      private final SavedStateHandle savedStateHandle;

      private volatile Provider<AddEditFragmentViewModel> addEditFragmentViewModelProvider;

      private volatile Provider<DeleteAllCompletedViewModel> deleteAllViewModelProvider;

      private volatile Provider<MainFragmentViewModel> mainFragmentViewModelProvider;

      private ViewModelCImpl(SavedStateHandle savedStateHandleParam) {
        this.savedStateHandle = savedStateHandleParam;
      }

      private AddEditFragmentViewModel addEditFragmentViewModel() {
        return new AddEditFragmentViewModel(savedStateHandle, DaggerApplication_HiltComponents_SingletonC.this.itemDao());
      }

      private Provider<AddEditFragmentViewModel> addEditFragmentViewModelProvider() {
        Object local = addEditFragmentViewModelProvider;
        if (local == null) {
          local = new SwitchingProvider<>(0);
          addEditFragmentViewModelProvider = (Provider<AddEditFragmentViewModel>) local;
        }
        return (Provider<AddEditFragmentViewModel>) local;
      }

      private DeleteAllCompletedViewModel deleteAllViewModel() {
        return new DeleteAllCompletedViewModel(DaggerApplication_HiltComponents_SingletonC.this.itemDao());
      }

      private Provider<DeleteAllCompletedViewModel> deleteAllViewModelProvider() {
        Object local = deleteAllViewModelProvider;
        if (local == null) {
          local = new SwitchingProvider<>(1);
          deleteAllViewModelProvider = (Provider<DeleteAllCompletedViewModel>) local;
        }
        return (Provider<DeleteAllCompletedViewModel>) local;
      }

      private MainFragmentViewModel mainFragmentViewModel() {
        return new MainFragmentViewModel(DaggerApplication_HiltComponents_SingletonC.this.itemDao());
      }

      private Provider<MainFragmentViewModel> mainFragmentViewModelProvider() {
        Object local = mainFragmentViewModelProvider;
        if (local == null) {
          local = new SwitchingProvider<>(2);
          mainFragmentViewModelProvider = (Provider<MainFragmentViewModel>) local;
        }
        return (Provider<MainFragmentViewModel>) local;
      }

      @Override
      public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
        return MapBuilder.<String, Provider<ViewModel>>newMapBuilder(3).put("com.example.todoapp.ui.addedit.AddEditFragmentViewModel", (Provider) addEditFragmentViewModelProvider()).put("com.example.todoapp.ui.items.deleteall.DeleteAllViewModel", (Provider) deleteAllViewModelProvider()).put("com.example.todoapp.ui.items.MainFragmentViewModel", (Provider) mainFragmentViewModelProvider()).build();
      }

      private final class SwitchingProvider<T> implements Provider<T> {
        private final int id;

        SwitchingProvider(int id) {
          this.id = id;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T get() {
          switch (id) {
            case 0: // com.example.todoapp.ui.addedit.AddEditFragmentViewModel 
            return (T) ViewModelCImpl.this.addEditFragmentViewModel();

            case 1: // com.example.todoapp.ui.items.deleteall.DeleteAllViewModel 
            return (T) ViewModelCImpl.this.deleteAllViewModel();

            case 2: // com.example.todoapp.ui.items.MainFragmentViewModel 
            return (T) ViewModelCImpl.this.mainFragmentViewModel();

            default: throw new AssertionError(id);
          }
        }
      }
    }
  }

  private final class ServiceCBuilder implements Application_HiltComponents.ServiceC.Builder {
    private Service service;

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public Application_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(service);
    }
  }

  private final class ServiceCImpl extends Application_HiltComponents.ServiceC {
    private ServiceCImpl(Service service) {

    }
  }
}
