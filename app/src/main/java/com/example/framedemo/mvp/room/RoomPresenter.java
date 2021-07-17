package com.example.framedemo.mvp.room;


import androidx.room.Room;

import com.example.framedemo.db.user.User;
import com.example.framedemo.mvp.BasePresenter;
import com.example.framedemo.repository.RoomRepository;
import com.example.framedemo.rx.SchedulerHelper;


import java.util.List;

public class RoomPresenter extends BasePresenter<RoomContract.View> implements RoomContract.Presenter {


    private RoomRepository repository;

    public RoomPresenter(RoomRepository repository) {
        this.repository = repository;
    }


    @Override
    public void getUsers() {
        addDisposable(repository.getUsers()
                .compose(SchedulerHelper.ioMain())
                .subscribe(users -> {
                    getView().usersData(users);
                }));

    }

    @Override
    public void updateUsers(List<User> users) {
        addDisposable(repository.insertUsers(users)
                .compose(SchedulerHelper.ioMain())
                .subscribe());
    }
}
