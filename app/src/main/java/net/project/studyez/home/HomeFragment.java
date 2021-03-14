package net.project.studyez.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentHomeBinding;
import net.project.studyez.home.quickStudy.QuickStudyFragment;
import net.project.studyez.main.MainActivity;

public class HomeFragment extends Fragment implements HomeContract.view {

    private HomePresenter presenter;
    private CardView quickStudyCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        presenter = new HomePresenter(this);
        binding.setPresenter(presenter);

        quickStudyCard = view.findViewById(R.id.CardView_QuickStudy);

        return view;
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, true);
    }

    @Override
    public void prepQuickStudy() {
        presenter.pressOnQuickStudy(new QuickStudyFragment(), R.id.main_container);
    }

}
