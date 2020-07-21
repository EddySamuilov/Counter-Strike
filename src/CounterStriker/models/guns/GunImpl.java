package CounterStriker.models.guns;

import CounterStriker.common.ExceptionMessages;

public abstract class GunImpl implements Gun {
    private String name;
    private int bulletsCount;

    protected GunImpl(String name, int bulletsCount) {
        this.setName(name);
        this.setBulletsCount(bulletsCount);
    }

    private void setName(String name) {
        if (name == null || name.trim().length() < 1) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_NAME);
        }
        this.name = name;
    }

    private void setBulletsCount(int bulletsCount) {
        if (bulletsCount < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_BULLETS_COUNT);
        }
        this.bulletsCount = bulletsCount;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public int getBulletsCount() {
        return this.bulletsCount;
    }

    @Override
    public int fire() {
        if (this.getClass().getSimpleName().equals("Rifle")){
            int bulletsShoot = this.bulletsCount -= 10;

            if (bulletsShoot < 0){
                return 0;
            }

            return 10;
        }else {
            int bulletsShoot = this.bulletsCount -= 1;
            if (bulletsShoot < 0) {
                return 0;
            }
            return 1;
        }
    }

}