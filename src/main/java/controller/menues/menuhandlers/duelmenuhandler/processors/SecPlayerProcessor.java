package controller.menues.menuhandlers.duelmenuhandler.processors;

import controller.menues.menuhandlers.duelmenuhandler.DuelProcessor;
import model.enums.MenusMassages.Duel;
import model.userProp.User;
import model.userProp.UserInfoType;

public class SecPlayerProcessor extends DuelProcessor {
    public SecPlayerProcessor(DuelProcessor handler) {
        super(handler);
    }

    public Duel process(String[] data) {
        User secondUser;
        if ((secondUser = User.getUserByUserInfo(data[1], UserInfoType.USERNAME)) == null) {
            return Duel.INVALID_SECOND_PLAYER;
        } else {
            if (secondUser == User.getUserByUserInfo(data[0], UserInfoType.USERNAME)) {
                return Duel.CANT_PLAY_WITH_YOURSELF;
            }
            return super.process(data);
        }
    }
}
