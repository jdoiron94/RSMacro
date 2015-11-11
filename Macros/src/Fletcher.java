import rs.macro.api.Macro;
import rs.macro.api.Manifest;
import rs.macro.api.access.*;
import rs.macro.api.access.input.Keyboard;
import rs.macro.api.access.input.Mouse;
import rs.macro.api.access.minimap.Minimap;
import rs.macro.api.util.Random;
import rs.macro.api.util.Renderable;
import rs.macro.api.util.Time;
import rs.macro.api.util.fx.MousePaint;
import rs.macro.api.util.fx.PixelOperator;
import rs.macro.api.util.fx.Text;
import rs.macro.api.util.fx.listener.PixelListener;
import rs.macro.api.util.fx.model.PixelModel;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Tyler Sedlar
 * @since 10/21/15
 */
@Manifest(name = "Fletcher", author = "Tyler", description = "Fletches bows",
        version = "1.0.0", banks = false)
public class Fletcher extends Macro implements Renderable, PixelListener {

    private static final Color MOUSE_OUTER = new Color(200, 85, 70);
    private static final Color MOUSE_INNER = new Color(240, 245, 45);
    private static final Color MOUSE_WAVE = new Color(240, 245, 45);
    private static final Color MOUSE_TRAIL = new Color(200, 85, 70);

    private static final int TARGET_ANGLE = 206;

    private static final PixelModel BANKER = PixelModel.fromString("#8E6523/10 #946926/10/8/-1 #976C26/10/3/-2 #C0956E/10/5/7");
    private static final PixelModel KNIFE = PixelModel.fromString("#766E6D/5 #766E6D/5/1/0 #766E6D/5/2/0 #726969/5/3/0 #A29695/5/4/0 #78706F/5/0/1 #78706F/5/1/1 #746B6A/5/2/1 #6D6565/5/3/1 #7A7171/5/-1/2 #7A7171/5/0/2 #766E6D/5/1/2 #6F6767/5/2/2 #A79B9A/5/3/2 #7C7373/5/-1/3 #7A7171/5/0/3 #726969/5/1/3 #998E8D/5/2/3 #7E7575/5/-2/4 #7C7373/5/-1/4 #766E6D/5/0/4 #6D6565/5/1/4 #AA9E9E/5/2/4 #7E7575/5/-2/5 #78706F/5/-1/5 #6F6767/5/0/5 #9C9191/5/1/5 #807777/5/-3/6 #7A7171/5/-2/6 #746B6A/5/-1/6 #6A6363/5/0/6 #AEA3A3/5/1/6 #7C7373/5/-3/7 #766E6D/5/-2/7 #6F6767/5/-1/7 #A29695/5/0/7 #553908/5/-4/8 #553908/5/-3/8 #A59999/5/-2/8 #938988/5/-1/8 #B2A7A6/5/0/8 #553908/5/-5/9 #553908/5/-4/9 #553908/5/-3/9 #553908/5/-2/9 #A59999/5/-1/9 #553908/5/-5/10 #553908/5/-4/10 #553908/5/-3/10 #553908/5/-2/10 #6B480D/5/-1/10 #553908/5/-6/11 #553908/5/-5/11 #553908/5/-4/11 #553908/5/-3/11 #6B480D/5/-2/11 #553908/5/-6/12 #553908/5/-5/12 #553908/5/-4/12 #6B480D/5/-3/12 #553908/5/-7/13 #553908/5/-6/13 #553908/5/-5/13 #6B480D/5/-4/13 #553908/5/-7/14 #553908/5/-6/14 #6B480D/5/-5/14 #553908/5/-8/15 #553908/5/-7/15 #6B480D/5/-6/15 #7C7373/5/-8/16 #9C9191/5/-7/16 #6F6767/5/-9/17 #8C8181/5/-8/17 #7A7171/5/-7/17 #625B5A/5/-10/18 #7A7171/5/-9/18 #887F7E/5/-8/18 #665E5E/5/-10/19 #766E6D/5/-9/19 #7E7575/5/-8/19 #5C5656/5/-10/20 #726969/5/-9/20");

    private static final PixelModel CREATE_IFACE = PixelModel.fromString("#800000/10 #9B9145/10/-128/0 #116861/10/318/-3");
    private static final Rectangle CREATE_IFACE_BOUNDS = new Rectangle(13, 353, 496, 39);

    private static final PixelModel ENTER_AMOUNT_IFACE = PixelModel.fromString("#000000/2 #000000/2/1/0 #000000/2/2/0 #000000/2/3/0 #000000/2/4/0 #000000/2/5/0 #000000/2/0/1 #000000/2/1/1 #000000/2/15/1 #000000/2/16/1 #000000/2/81/1 #000000/2/82/1 #000000/2/0/2 #000000/2/1/2 #000000/2/15/2 #000000/2/16/2 #000000/2/81/2 #000000/2/82/2 #000000/2/0/3 #000000/2/1/3 #000000/2/15/3 #000000/2/16/3 #000000/2/81/3 #000000/2/82/3 #000000/2/0/4 #000000/2/1/4 #000000/2/2/4 #000000/2/3/4 #000000/2/7/4 #000000/2/8/4 #000000/2/9/4 #000000/2/10/4 #000000/2/11/4 #000000/2/15/4 #000000/2/16/4 #000000/2/17/4 #000000/2/18/4 #000000/2/22/4 #000000/2/23/4 #000000/2/24/4 #000000/2/25/4 #000000/2/29/4 #000000/2/30/4 #000000/2/31/4 #000000/2/32/4 #000000/2/40/4 #000000/2/41/4 #000000/2/42/4 #000000/2/43/4 #000000/2/48/4 #000000/2/49/4 #000000/2/50/4 #000000/2/51/4 #000000/2/52/4 #000000/2/53/4 #000000/2/58/4 #000000/2/59/4 #000000/2/60/4 #000000/2/61/4 #000000/2/65/4 #000000/2/66/4 #000000/2/69/4 #000000/2/70/4 #000000/2/73/4 #000000/2/74/4 #000000/2/75/4 #000000/2/76/4 #000000/2/77/4 #000000/2/81/4 #000000/2/82/4 #000000/2/83/4 #000000/2/84/4 #000000/2/87/4 #000000/2/88/4 #000000/2/0/5 #000000/2/1/5 #000000/2/7/5 #000000/2/8/5 #000000/2/9/5 #000000/2/11/5 #000000/2/12/5 #000000/2/15/5 #000000/2/16/5 #000000/2/21/5 #000000/2/22/5 #000000/2/25/5 #000000/2/26/5 #000000/2/29/5 #000000/2/30/5 #000000/2/31/5 #000000/2/39/5 #000000/2/40/5 #000000/2/43/5 #000000/2/44/5 #000000/2/47/5 #000000/2/48/5 #000000/2/50/5 #000000/2/51/5 #000000/2/53/5 #000000/2/54/5 #000000/2/57/5 #000000/2/58/5 #000000/2/61/5 #000000/2/62/5 #000000/2/65/5 #000000/2/66/5 #000000/2/69/5 #000000/2/70/5 #000000/2/73/5 #000000/2/74/5 #000000/2/75/5 #000000/2/77/5 #000000/2/78/5 #000000/2/81/5 #000000/2/82/5 #000000/2/0/6 #000000/2/1/6 #000000/2/7/6 #000000/2/8/6 #000000/2/11/6 #000000/2/12/6 #000000/2/15/6 #000000/2/16/6 #000000/2/21/6 #000000/2/22/6 #000000/2/25/6 #000000/2/26/6 #000000/2/29/6 #000000/2/30/6 #000000/2/40/6 #000000/2/41/6 #000000/2/42/6 #000000/2/43/6 #000000/2/44/6 #000000/2/47/6 #000000/2/48/6 #000000/2/50/6 #000000/2/51/6 #000000/2/53/6 #000000/2/54/6 #000000/2/57/6 #000000/2/58/6 #000000/2/61/6 #000000/2/62/6 #000000/2/65/6 #000000/2/66/6 #000000/2/69/6 #000000/2/70/6 #000000/2/73/6 #000000/2/74/6 #000000/2/77/6 #000000/2/78/6 #000000/2/81/6 #000000/2/82/6 #000000/2/0/7 #000000/2/1/7 #000000/2/7/7 #000000/2/8/7 #000000/2/11/7 #000000/2/12/7 #000000/2/15/7 #000000/2/16/7 #000000/2/21/7 #000000/2/22/7 #000000/2/23/7 #000000/2/24/7 #000000/2/25/7 #000000/2/29/7 #000000/2/30/7 #000000/2/39/7 #000000/2/40/7 #000000/2/43/7 #000000/2/44/7 #000000/2/47/7 #000000/2/48/7 #000000/2/50/7 #000000/2/51/7 #000000/2/53/7 #000000/2/54/7 #000000/2/57/7 #000000/2/58/7 #000000/2/61/7 #000000/2/62/7 #000000/2/65/7 #000000/2/66/7 #000000/2/69/7 #000000/2/70/7 #000000/2/73/7 #000000/2/74/7 #000000/2/77/7 #000000/2/78/7 #000000/2/81/7 #000000/2/82/7 #000000/2/0/8 #000000/2/1/8 #000000/2/7/8 #000000/2/8/8 #000000/2/11/8 #000000/2/12/8 #000000/2/15/8 #000000/2/16/8 #000000/2/21/8 #000000/2/22/8 #000000/2/29/8 #000000/2/30/8 #000000/2/39/8 #000000/2/40/8 #000000/2/43/8 #000000/2/44/8 #000000/2/47/8 #000000/2/48/8 #000000/2/50/8 #000000/2/51/8 #000000/2/53/8 #000000/2/54/8 #000000/2/57/8 #000000/2/58/8 #000000/2/61/8 #000000/2/62/8 #000000/2/65/8 #000000/2/66/8 #000000/2/69/8 #000000/2/70/8 #000000/2/73/8 #000000/2/74/8 #000000/2/77/8 #000000/2/78/8 #000000/2/81/8 #000000/2/82/8 #000000/2/0/9 #000000/2/1/9 #000000/2/2/9 #000000/2/3/9 #000000/2/4/9 #000000/2/5/9 #000000/2/7/9 #000000/2/8/9 #000000/2/11/9 #000000/2/12/9 #000000/2/16/9 #000000/2/17/9 #000000/2/18/9 #000000/2/22/9 #000000/2/23/9 #000000/2/24/9 #000000/2/25/9 #000000/2/26/9 #000000/2/29/9 #000000/2/30/9 #000000/2/40/9 #000000/2/41/9 #000000/2/42/9 #000000/2/43/9 #000000/2/44/9 #000000/2/47/9 #000000/2/48/9 #000000/2/50/9 #000000/2/51/9 #000000/2/53/9 #000000/2/54/9 #000000/2/58/9 #000000/2/59/9 #000000/2/60/9 #000000/2/61/9 #000000/2/66/9 #000000/2/67/9 #000000/2/68/9 #000000/2/69/9 #000000/2/73/9 #000000/2/74/9 #000000/2/77/9 #000000/2/78/9 #000000/2/82/9 #000000/2/83/9 #000000/2/84/9 #000000/2/87/9 #000000/2/88/9");
    private static final Rectangle ENTER_AMOUNT_IFACE_BOUNDS = new Rectangle(206, 390, 103, 22);

    private static final PixelModel LEVEL_UP_IFACE = PixelModel.fromString("#AC9D81/10 #000080/10/110/3 #6C140D/10/74/-9 #B3A589/10/455/1");
    private static final Rectangle LEVEL_UP_IFACE_BOUNDS = new Rectangle(18, 360, 490, 72);

    private static final PixelModel COLLECT_IFACE = PixelModel.fromString("#FF981F/2 #FF981F/2/1/0 #FF981F/2/2/0 #FF981F/2/14/0 #FF981F/2/15/0 #FF981F/2/18/0 #FF981F/2/19/0 #FF981F/2/67/0 #FF981F/2/68/0 #FF981F/2/69/0 #FF981F/2/70/0 #FF981F/2/71/0 #FF981F/2/-1/1 #FF981F/2/0/1 #FF981F/2/2/1 #FF981F/2/3/1 #FF981F/2/14/1 #FF981F/2/15/1 #FF981F/2/18/1 #FF981F/2/19/1 #FF981F/2/37/1 #FF981F/2/38/1 #FF981F/2/67/1 #FF981F/2/68/1 #FF981F/2/71/1 #FF981F/2/72/1 #FF981F/2/-2/2 #FF981F/2/-1/2 #FF981F/2/14/2 #FF981F/2/15/2 #FF981F/2/18/2 #FF981F/2/19/2 #FF981F/2/37/2 #FF981F/2/38/2 #FF981F/2/67/2 #FF981F/2/68/2 #FF981F/2/71/2 #FF981F/2/72/2 #FF981F/2/-2/3 #FF981F/2/-1/3 #FF981F/2/14/3 #FF981F/2/15/3 #FF981F/2/18/3 #FF981F/2/19/3 #FF981F/2/37/3 #FF981F/2/38/3 #FF981F/2/43/3 #FF981F/2/44/3 #FF981F/2/67/3 #FF981F/2/68/3 #FF981F/2/71/3 #FF981F/2/72/3 #FF981F/2/-2/4 #FF981F/2/-1/4 #FF981F/2/7/4 #FF981F/2/8/4 #FF981F/2/9/4 #FF981F/2/10/4 #FF981F/2/14/4 #FF981F/2/15/4 #FF981F/2/18/4 #FF981F/2/19/4 #FF981F/2/23/4 #FF981F/2/24/4 #FF981F/2/25/4 #FF981F/2/26/4 #FF981F/2/31/4 #FF981F/2/32/4 #FF981F/2/33/4 #FF981F/2/34/4 #FF981F/2/37/4 #FF981F/2/38/4 #FF981F/2/39/4 #FF981F/2/40/4 #FF981F/2/48/4 #FF981F/2/49/4 #FF981F/2/50/4 #FF981F/2/51/4 #FF981F/2/55/4 #FF981F/2/56/4 #FF981F/2/57/4 #FF981F/2/58/4 #FF981F/2/59/4 #FF981F/2/67/4 #FF981F/2/68/4 #FF981F/2/69/4 #FF981F/2/70/4 #FF981F/2/71/4 #FF981F/2/76/4 #FF981F/2/77/4 #FF981F/2/78/4 #FF981F/2/79/4 #FF981F/2/83/4 #FF981F/2/84/4 #FF981F/2/87/4 #FF981F/2/88/4 #FF981F/2/-2/5 #FF981F/2/-1/5 #FF981F/2/6/5 #FF981F/2/7/5 #FF981F/2/10/5 #FF981F/2/11/5 #FF981F/2/14/5 #FF981F/2/15/5 #FF981F/2/18/5 #FF981F/2/19/5 #FF981F/2/22/5 #FF981F/2/23/5 #FF981F/2/26/5 #FF981F/2/27/5 #FF981F/2/30/5 #FF981F/2/31/5 #FF981F/2/37/5 #FF981F/2/38/5 #FF981F/2/43/5 #FF981F/2/44/5 #FF981F/2/47/5 #FF981F/2/48/5 #FF981F/2/51/5 #FF981F/2/52/5 #FF981F/2/55/5 #FF981F/2/56/5 #FF981F/2/57/5 #FF981F/2/59/5 #FF981F/2/60/5 #FF981F/2/67/5 #FF981F/2/68/5 #FF981F/2/71/5 #FF981F/2/72/5 #FF981F/2/75/5 #FF981F/2/76/5 #FF981F/2/79/5 #FF981F/2/80/5 #FF981F/2/84/5 #FF981F/2/85/5 #FF981F/2/86/5 #FF981F/2/87/5 #FF981F/2/-2/6 #FF981F/2/-1/6 #FF981F/2/6/6 #FF981F/2/7/6 #FF981F/2/10/6 #FF981F/2/11/6 #FF981F/2/14/6 #FF981F/2/15/6 #FF981F/2/18/6 #FF981F/2/19/6 #FF981F/2/22/6 #FF981F/2/23/6 #FF981F/2/26/6 #FF981F/2/27/6 #FF981F/2/30/6 #FF981F/2/31/6 #FF981F/2/37/6 #FF981F/2/38/6 #FF981F/2/43/6 #FF981F/2/44/6 #FF981F/2/47/6 #FF981F/2/48/6 #FF981F/2/51/6 #FF981F/2/52/6 #FF981F/2/55/6 #FF981F/2/56/6 #FF981F/2/59/6 #FF981F/2/60/6 #FF981F/2/67/6 #FF981F/2/68/6 #FF981F/2/71/6 #FF981F/2/72/6 #FF981F/2/75/6 #FF981F/2/76/6 #FF981F/2/79/6 #FF981F/2/80/6 #FF981F/2/85/6 #FF981F/2/86/6 #FF981F/2/-2/7 #FF981F/2/-1/7 #FF981F/2/6/7 #FF981F/2/7/7 #FF981F/2/10/7 #FF981F/2/11/7 #FF981F/2/14/7 #FF981F/2/15/7 #FF981F/2/18/7 #FF981F/2/19/7 #FF981F/2/22/7 #FF981F/2/23/7 #FF981F/2/24/7 #FF981F/2/25/7 #FF981F/2/26/7 #FF981F/2/30/7 #FF981F/2/31/7 #FF981F/2/37/7 #FF981F/2/38/7 #FF981F/2/43/7 #FF981F/2/44/7 #FF981F/2/47/7 #FF981F/2/48/7 #FF981F/2/51/7 #FF981F/2/52/7 #FF981F/2/55/7 #FF981F/2/56/7 #FF981F/2/59/7 #FF981F/2/60/7 #FF981F/2/67/7 #FF981F/2/68/7 #FF981F/2/71/7 #FF981F/2/72/7 #FF981F/2/75/7 #FF981F/2/76/7 #FF981F/2/79/7 #FF981F/2/80/7 #FF981F/2/84/7 #FF981F/2/85/7 #FF981F/2/86/7 #FF981F/2/87/7 #FF981F/2/-1/8 #FF981F/2/0/8 #FF981F/2/2/8 #FF981F/2/3/8 #FF981F/2/6/8 #FF981F/2/7/8 #FF981F/2/10/8 #FF981F/2/11/8 #FF981F/2/14/8 #FF981F/2/15/8 #FF981F/2/18/8 #FF981F/2/19/8 #FF981F/2/22/8 #FF981F/2/23/8 #FF981F/2/30/8 #FF981F/2/31/8 #FF981F/2/37/8 #FF981F/2/38/8 #FF981F/2/43/8 #FF981F/2/44/8 #FF981F/2/47/8 #FF981F/2/48/8 #FF981F/2/51/8 #FF981F/2/52/8 #FF981F/2/55/8 #FF981F/2/56/8 #FF981F/2/59/8 #FF981F/2/60/8 #FF981F/2/67/8 #FF981F/2/68/8 #FF981F/2/71/8 #FF981F/2/72/8 #FF981F/2/75/8 #FF981F/2/76/8 #FF981F/2/79/8 #FF981F/2/80/8 #FF981F/2/84/8 #FF981F/2/85/8 #FF981F/2/86/8 #FF981F/2/87/8 #FF981F/2/0/9 #FF981F/2/1/9 #FF981F/2/2/9 #FF981F/2/7/9 #FF981F/2/8/9 #FF981F/2/9/9 #FF981F/2/10/9 #FF981F/2/14/9 #FF981F/2/15/9 #FF981F/2/18/9 #FF981F/2/19/9 #FF981F/2/23/9 #FF981F/2/24/9 #FF981F/2/25/9 #FF981F/2/26/9 #FF981F/2/27/9 #FF981F/2/31/9 #FF981F/2/32/9 #FF981F/2/33/9 #FF981F/2/34/9 #FF981F/2/38/9 #FF981F/2/39/9 #FF981F/2/40/9 #FF981F/2/43/9 #FF981F/2/44/9 #FF981F/2/48/9 #FF981F/2/49/9 #FF981F/2/50/9 #FF981F/2/51/9 #FF981F/2/55/9 #FF981F/2/56/9 #FF981F/2/59/9 #FF981F/2/60/9 #FF981F/2/67/9 #FF981F/2/68/9 #FF981F/2/69/9 #FF981F/2/70/9 #FF981F/2/71/9 #FF981F/2/76/9 #FF981F/2/77/9 #FF981F/2/78/9 #FF981F/2/79/9 #FF981F/2/83/9 #FF981F/2/84/9 #FF981F/2/87/9 #FF981F/2/88/9");
    private static final Rectangle COLLECT_IFACE_BOUNDS = new Rectangle(208, 56, 103, 18);
    private static final Rectangle COLLECT_IFACE_CLOSE_BOUNDS = new Rectangle(465, 58, 14, 15);

    private static final Log LOG = Log.YEW;
    private static final FletchType TYPE = FletchType.LONG_BOW;

    private int fletched = 0;
    private String status = "N/A";

    @Override
    public void atStart() {
        Mouse.setSpeed(1);
    }

    private boolean setAngle = false;
    
    private void setStatus(String status) {
        this.status = status;
        System.out.println(status);
    }

    @Override
    public int loop() {
        if (!setAngle && Math.abs(Minimap.angle() - TARGET_ANGLE) > 5) {
            setStatus("Setting angle");
            setAngle = Camera.setAngle(TARGET_ANGLE);
        } else if (collecting()) {
            setStatus("Closing collect");
            closeCollect();
        } else {
            if (Bank.viewing()) {
                if (Inventory.findSlot(LOG.model) != null) {
                    setStatus("Closing bank");
                    Bank.close();
                } else {
                    if (Inventory.count() > 1) {
                        setStatus("Depositing items");
                        int slotIndex = (Inventory.SLOTS.length - 1); // TODO: implement Inventory#findLastSlot
                        Mouse.move(Inventory.SLOTS[slotIndex]);
                        if (GameMenu.selectIndex(4)) { // Deposit-All
                            Time.waitFor(2500, () -> !Inventory.hasItem(slotIndex));
                        }
                    } else {
                        setStatus("Withdrawing items");
                        Rectangle bounds = Bank.findSlot(LOG.model);
                        if (bounds == null) {
                            System.out.println("You are out of logs.");
                            return -1;
                        }
                        Mouse.move(bounds);
                        if (GameMenu.selectIndex(4)) { // Withdraw-All
                            Time.waitFor(2500, () -> Inventory.findSlot(LOG.model) != null);
                        }
                    }
                }
            } else {
                Rectangle knife = Inventory.findSlot(KNIFE);
                if (knife == null) {
                    System.out.println("A knife is needed to fletch!");
                    return -1;
                }
                Rectangle log = Inventory.findSlot(LOG.model);
                if (log != null) {
                    setStatus("Fletching");
                    fletch(knife, log);
                } else {
                    setStatus("Opening bank");
                    openBank();
                }
            }
        }
        return Random.nextInt(50, 100);
    }

    private boolean collecting() {
        return operator().builder()
                .bounds(COLLECT_IFACE_BOUNDS)
                .model(COLLECT_IFACE)
                .first() != null;
    }

    private boolean closeCollect() {
        Mouse.click(COLLECT_IFACE_CLOSE_BOUNDS, true);
        return Time.waitFor(2500, () -> !collecting());
    }

    private boolean selecting() {
        return operator().builder()
                .bounds(CREATE_IFACE_BOUNDS)
                .model(CREATE_IFACE)
                .first() != null;
    }

    private boolean inputting() {
        return operator().builder()
                .bounds(ENTER_AMOUNT_IFACE_BOUNDS)
                .model(ENTER_AMOUNT_IFACE)
                .first() != null;
    }

    private boolean leveled() {
        return operator().builder()
                .bounds(LEVEL_UP_IFACE_BOUNDS)
                .model(LEVEL_UP_IFACE)
                .first() != null;
    }

    private void fletch(Rectangle knife, Rectangle log) {
        if (selecting()) {
            setStatus("Selecting Make-X");
            Mouse.move(TYPE.bounds);
            if (GameMenu.selectIndex(3)) { // Make-X
                Time.waitFor(2500, this::inputting);
            }
        } else if (inputting()) {
            setStatus("Typing 99");
            Keyboard.send("99");
            if (Time.waitFor(2500, () -> !inputting())) {
                setStatus("Fletching bows");
                final AtomicReference<Rectangle> lastSlot =
                        new AtomicReference<>(null);
                Time.waitFor(60000, () -> {
                    if (leveled()) {
                        setStatus("Leveled up");
                        return true;
                    }
                    Rectangle slot = Inventory.findSlot(LOG.model);
                    if (slot != lastSlot.get() && lastSlot.get() != null) {
                        fletched++;
                    }
                    lastSlot.set(slot);
                    return slot == null;
                });
                setStatus("Finished fletching");
            }
        } else {
            setStatus("Using items");
            Mouse.click(knife, true);
            Time.sleep(25, 50);
            Mouse.click(log, true);
            Time.waitFor(2500, this::selecting);
        }
    }

    private boolean openBank() {
        Point center = Viewport.center();
        Point banker = operator().builder()
                .model(BANKER).query()
                .sorted(
                        (a, b) -> Double.compare(a.distance(center), b.distance(center))
                ).findFirst().orElse(null);
        if (banker != null) {
            Mouse.move(banker);
            if (GameMenu.selectIndex(1)) { // Bank
                return Time.waitFor(2500, Bank::viewing);
            }
        }
        return false;
    }

    private static final String RUNTIME_FORMAT = "RUNTIME - %s";
    private static final String FLETCHED_FORMAT = "FLETCHED - %s (%s/HR)";
    private static final String STATUS_FORMAT = "STATUS - %s";

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        MousePaint.drawMouseWaves(g, MOUSE_WAVE);
        MousePaint.drawTrail(g, MOUSE_TRAIL);
        MousePaint.drawOval(g, MOUSE_OUTER, MOUSE_INNER);
        String runtime = Time.format(runtime());
        Text.drawRuneString(g, String.format(RUNTIME_FORMAT, runtime),
                8, Viewport.height() - 30, Color.YELLOW);
        int hourlyFletched = Time.hourly(runtime(), fletched);
        Text.drawRuneString(g, String.format(FLETCHED_FORMAT, fletched, hourlyFletched),
                8, Viewport.height() - 15, Color.YELLOW);
        Text.drawRuneString(g, String.format(STATUS_FORMAT, status.toUpperCase()),
                8, Viewport.height(), Color.YELLOW);
    }

    @Override
    public void onPixelsUpdated(PixelOperator operator) {

    }

    private enum Log {
        WILLOW("#635B37/5 #69613B/5/1/0 #1F1C11/5/19/0 #70663E/5/-1/1 #70663E/5/0/1 #70663E/5/1/1 #564D1F/5/2/1 #3B3414/5/19/1 #70663E/5/-1/2 #70663E/5/0/2 #70663E/5/1/2 #564D1F/5/2/2 #564D1F/5/3/2 #564D1F/5/10/2 #564D1F/5/11/2 #564D1F/5/12/2 #564D1F/5/13/2 #564D1F/5/14/2 #564D1F/5/15/2 #70663E/5/-1/3 #70663E/5/0/3 #70663E/5/1/3 #6D643C/5/2/3 #564D1F/5/3/3 #564D1F/5/4/3 #564D1F/5/7/3 #564D1F/5/8/3 #564D1F/5/9/3 #564D1F/5/10/3 #564D1F/5/11/3 #564D1F/5/12/3 #564D1F/5/13/3 #564D1F/5/14/3 #564D1F/5/15/3 #3B3414/5/18/3 #70663E/5/-1/4 #70663E/5/0/4 #70663E/5/1/4 #6D643C/5/2/4 #564D1F/5/3/4 #564D1F/5/4/4 #564D1F/5/5/4 #564D1F/5/6/4 #564D1F/5/7/4 #564D1F/5/8/4 #564D1F/5/9/4 #564D1F/5/10/4 #564D1F/5/11/4 #564D1F/5/12/4 #564D1F/5/13/4 #362F11/5/18/4 #70663E/5/-1/5 #70663E/5/0/5 #70663E/5/1/5 #70663E/5/2/5 #70663E/5/3/5 #564D1F/5/4/5 #564D1F/5/5/5 #564D1F/5/6/5 #564D1F/5/7/5 #564D1F/5/8/5 #564D1F/5/9/5 #564D1F/5/10/5 #3B3414/5/16/5 #362F11/5/17/5 #362F11/5/18/5 #70663E/5/-1/6 #70663E/5/0/6 #70663E/5/1/6 #70663E/5/2/6 #564D1F/5/3/6 #564D1F/5/4/6 #564D1F/5/5/6 #564D1F/5/6/6 #564D1F/5/7/6 #6D643C/5/-4/7 #70663E/5/0/7 #70663E/5/1/7 #69613B/5/2/7 #3B3414/5/6/7 #3B3414/5/7/7 #564D1F/5/19/7 #6D643C/5/-4/8 #69613B/5/-3/8 #665E39/5/-2/8 #69613B/5/1/8 #564D1F/5/18/8 #564D1F/5/19/8 #756B42/5/-5/9 #70663E/5/-4/9 #69613B/5/-3/9 #665E39/5/-2/9 #635B37/5/1/9 #564D1F/5/17/9 #564D1F/5/18/9 #564D1F/5/19/9 #70663E/5/-5/10 #70663E/5/-4/10 #6D643C/5/-3/10 #665E39/5/-2/10 #70663E/5/1/10 #69613B/5/2/10 #635B37/5/3/10 #564D1F/5/16/10 #564D1F/5/17/10 #564D1F/5/18/10 #564D1F/5/19/10 #69613B/5/-5/11 #70663E/5/-4/11 #6D643C/5/-3/11 #69613B/5/-2/11 #635B37/5/0/11 #70663E/5/1/11 #70663E/5/2/11 #70663E/5/3/11 #6D643C/5/4/11 #564D1F/5/5/11 #564D1F/5/6/11 #564D1F/5/14/11 #564D1F/5/15/11 #564D1F/5/16/11 #564D1F/5/17/11 #564D1F/5/18/11 #564D1F/5/19/11 #665E39/5/-5/12 #70663E/5/-4/12 #6D643C/5/-3/12 #69613B/5/-2/12 #70663E/5/0/12 #70663E/5/1/12 #70663E/5/2/12 #70663E/5/3/12 #6D643C/5/4/12 #564D1F/5/5/12 #564D1F/5/6/12 #564D1F/5/13/12 #564D1F/5/14/12 #564D1F/5/15/12 #564D1F/5/16/12 #564D1F/5/17/12 #564D1F/5/18/12 #564D1F/5/19/12 #3B3414/5/22/12 #70663E/5/-4/13 #6D643C/5/-3/13 #69613B/5/-2/13 #635B37/5/-1/13 #635B37/5/0/13 #70663E/5/1/13 #70663E/5/2/13 #70663E/5/3/13 #6D643C/5/4/13 #564D1F/5/5/13 #564D1F/5/6/13 #564D1F/5/12/13 #564D1F/5/13/13 #564D1F/5/14/13 #564D1F/5/15/13 #564D1F/5/16/13 #362F11/5/21/13 #2F2B11/5/22/13 #665E39/5/-3/14 #665E39/5/-2/14 #70663E/5/1/14 #70663E/5/2/14 #70663E/5/3/14 #6D643C/5/4/14 #564D1F/5/5/14 #564D1F/5/6/14 #564D1F/5/7/14 #564D1F/5/11/14 #564D1F/5/12/14 #564D1F/5/13/14 #70663E/5/1/15 #70663E/5/2/15 #70663E/5/3/15 #70663E/5/4/15 #564D1F/5/5/15 #564D1F/5/6/15 #564D1F/5/7/15 #564D1F/5/8/15 #70663E/5/1/16 #70663E/5/2/16 #70663E/5/3/16 #6D643C/5/4/16 #6D643C/5/5/16 #564D1F/5/6/16 #564D1F/5/7/16 #70663E/5/2/17 #70663E/5/3/17 #6D643C/5/4/17 #564D1F/5/5/17 #564D1F/5/6/17 #70663E/5/3/18 #564D1F/5/4/18"),
        YEW("#4A2C09/10 #4A2C09/10/1/0 #4E3009/10/10/0 #5D4509/10/3/0 #5D4509/10/4/0 #5D4509/10/5/0 #584209/10/6/0 #584209/10/7/0 #584209/10/8/0 #584209/10/9/0 #584209/10/10/0 #584209/10/11/0 #5D4509/10/12/0 #5D4509/10/13/0 #5D4509/10/14/0 #5D4509/10/15/0 #584209/10/16/0 #584209/10/17/0 #533F09/10/18/0 #4A3709/10/19/0 #1F1200/10/20/0 #533409/10/0/1 #876F3F/10/1/1 #876F3F/10/2/1 #624A09/10/3/1 #5D4509/10/4/1 #5D4509/10/5/1 #5D4509/10/6/1 #584209/10/7/1 #584209/10/8/1 #584209/10/9/1 #5D4509/10/10/1 #5D4509/10/11/1 #5D4509/10/12/1 #5D4509/10/13/1 #5D4509/10/14/1 #5D4509/10/15/1 #5D4509/10/16/1 #5D4509/10/17/1 #533F09/10/18/1 #4A3709/10/19/1 #443200/10/20/1 #876F3F/10/0/2 #876F3F/10/1/2 #876F3F/10/2/2 #624A09/10/3/2 #624A09/10/4/2 #5D4509/10/5/2 #5D4509/10/6/2 #5D4509/10/7/2 #5D4509/10/8/2 #5D4509/10/9/2 #5D4509/10/10/2 #624A09/10/11/2 #624A09/10/12/2 #624A09/10/13/2 #624A09/10/14/2 #624A09/10/15/2 #624A09/10/16/2 #5D4509/10/17/2 #533F09/10/18/2 #4A3709/10/19/2 #876F3F/10/0/3 #876F3F/10/1/3 #876F3F/10/2/3 #4E3009/10/3/3 #624A09/10/4/3 #624A09/10/5/3 #5D4509/10/6/3 #5D4509/10/7/3 #624A09/10/8/3 #624A09/10/9/3 #624A09/10/10/3 #624A09/10/11/3 #624A09/10/12/3 #624A09/10/13/3 #624A09/10/14/3 #624A09/10/15/3 #624A09/10/16/3 #584209/10/17/3 #4E3B09/10/18/3 #443200/10/19/3 #876F3F/10/0/4 #876F3F/10/1/4 #876F3F/10/2/4 #4E3009/10/3/4 #624A09/10/4/4 #624A09/10/5/4 #624A09/10/6/4 #624A09/10/7/4 #624A09/10/8/4 #624A09/10/9/4 #624A09/10/10/4 #624A09/10/11/4 #624A09/10/12/4 #624A09/10/13/4 #624A09/10/14/4 #5D4509/10/15/4 #584209/10/16/4 #533F09/10/17/4 #4A3709/10/18/4 #3E2E00/10/19/4 #876F3F/10/0/5 #876F3F/10/1/5 #876F3F/10/2/5 #876F3F/10/3/5 #533409/10/4/5 #624A09/10/5/5 #624A09/10/6/5 #624A09/10/7/5 #624A09/10/8/5 #624A09/10/9/5 #624A09/10/10/5 #624A09/10/11/5 #5D4509/10/12/5 #584209/10/13/5 #533F09/10/14/5 #4E3B09/10/15/5 #4A3709/10/16/5 #443200/10/17/5 #3E2E00/10/18/5 #3E2E00/10/19/5 #372100/10/20/5 #301C00/10/21/5 #4E3B09/10/-3/6 #4E3B09/10/-2/6 #4E3B09/10/-1/6 #876F3F/10/0/6 #876F3F/10/1/6 #876F3F/10/2/6 #533409/10/3/6 #624A09/10/4/6 #624A09/10/5/6 #624A09/10/6/6 #624A09/10/7/6 #624A09/10/8/6 #5D4509/10/9/6 #5D4509/10/10/6 #584209/10/11/6 #533F09/10/12/6 #4E3B09/10/13/6 #4A3709/10/14/6 #4A3709/10/15/6 #4A3709/10/16/6 #4E3B09/10/17/6 #4E3B09/10/18/6 #533F09/10/19/6 #533F09/10/20/6 #4E3B09/10/21/6 #4A3709/10/22/6 #442800/10/-4/7 #533409/10/-3/7 #533F09/10/-2/7 #533F09/10/-1/7 #533F09/10/0/7 #533409/10/1/7 #533409/10/2/7 #4E3009/10/3/7 #5D4509/10/4/7 #5D4509/10/5/7 #5D4509/10/6/7 #443200/10/7/7 #443200/10/8/7 #4A3709/10/9/7 #4A3709/10/10/7 #4E3B09/10/11/7 #4E3B09/10/12/7 #533F09/10/13/7 #533F09/10/14/7 #533F09/10/15/7 #584209/10/16/7 #584209/10/17/7 #5D4509/10/18/7 #5D4509/10/19/7 #624A09/10/20/7 #5D4509/10/21/7 #584209/10/22/7 #533F09/10/23/7 #4E3B09/10/24/7 #4A2C09/10/-4/8 #826B3C/10/-3/8 #7C653B/10/-2/8 #786339/10/-1/8 #584209/10/0/8 #584209/10/1/8 #4E3009/10/2/8 #5D4509/10/3/8 #4A3709/10/4/8 #4A3709/10/5/8 #4A3709/10/6/8 #4A3709/10/7/8 #4A3709/10/8/8 #4E3B09/10/9/8 #4E3B09/10/10/8 #533F09/10/11/8 #533F09/10/12/8 #584209/10/13/8 #584209/10/14/8 #584209/10/15/8 #5D4509/10/16/8 #5D4509/10/17/8 #5D4509/10/18/8 #624A09/10/19/8 #624A09/10/20/8 #5D4509/10/21/8 #584209/10/22/8 #533F09/10/23/8 #4E3B09/10/24/8 #583509/10/-4/9 #856D3E/10/-3/9 #7F683B/10/-2/9 #786339/10/-1/9 #584209/10/0/9 #584209/10/1/9 #4A2C09/10/2/9 #4A2C09/10/3/9 #533F09/10/4/9 #533F09/10/5/9 #533F09/10/6/9 #533F09/10/7/9 #533F09/10/8/9 #533F09/10/9/9 #533F09/10/10/9 #584209/10/11/9 #584209/10/12/9 #5D4509/10/13/9 #5D4509/10/14/9 #5D4509/10/15/9 #5D4509/10/16/9 #5D4509/10/17/9 #624A09/10/18/9 #624A09/10/19/9 #624A09/10/20/9 #5D4509/10/21/9 #584209/10/22/9 #533F09/10/23/9 #4E3B09/10/24/9 #533409/10/-4/10 #876F3F/10/-3/10 #7F683B/10/-2/10 #7C653B/10/-1/10 #584209/10/0/10 #584209/10/1/10 #533409/10/2/10 #4E3009/10/3/10 #4E3009/10/4/10 #5D4509/10/5/10 #5D4509/10/6/10 #5D4509/10/7/10 #5D4509/10/8/10 #5D4509/10/9/10 #5D4509/10/10/10 #5D4509/10/11/10 #5D4509/10/12/10 #5D4509/10/13/10 #5D4509/10/14/10 #5D4509/10/15/10 #5D4509/10/16/10 #624A09/10/17/10 #624A09/10/18/10 #624A09/10/19/10 #624A09/10/20/10 #5D4509/10/21/10 #584209/10/22/10 #533F09/10/23/10 #4E3B09/10/24/10 #4E3009/10/-4/11 #876F3F/10/-3/11 #826B3C/10/-2/11 #7C653B/10/-1/11 #584209/10/0/11 #4A2C09/10/1/11 #876F3F/10/2/11 #876F3F/10/3/11 #876F3F/10/4/11 #533409/10/5/11 #624A09/10/6/11 #624A09/10/7/11 #5D4509/10/8/11 #5D4509/10/9/11 #5D4509/10/10/11 #5D4509/10/11/11 #5D4509/10/12/11 #5D4509/10/13/11 #5D4509/10/14/11 #624A09/10/15/11 #624A09/10/16/11 #624A09/10/17/11 #624A09/10/18/11 #624A09/10/19/11 #624A09/10/20/11 #5D4509/10/21/11 #584209/10/22/11 #533F09/10/23/11 #4E3B09/10/24/11 #4E3009/10/-4/12 #856D3E/10/-3/12 #826B3C/10/-2/12 #7C653B/10/-1/12 #584209/10/0/12 #533409/10/1/12 #876F3F/10/2/12 #876F3F/10/3/12 #876F3F/10/4/12 #533409/10/5/12 #624A09/10/6/12 #624A09/10/7/12 #5D4509/10/8/12 #5D4509/10/9/12 #5D4509/10/10/12 #5D4509/10/11/12 #5D4509/10/12/12 #5D4509/10/13/12 #624A09/10/14/12 #624A09/10/15/12 #624A09/10/16/12 #624A09/10/17/12 #624A09/10/18/12 #624A09/10/19/12 #624A09/10/20/12 #584209/10/21/12 #4E3B09/10/22/12 #443200/10/23/12 #4A2C09/10/-4/13 #533409/10/-3/13 #826B3C/10/-2/13 #7F683B/10/-1/13 #756137/10/0/13 #4A2C09/10/1/13 #876F3F/10/2/13 #876F3F/10/3/13 #876F3F/10/4/13 #533409/10/5/13 #624A09/10/6/13 #624A09/10/7/13 #5D4509/10/8/13 #5D4509/10/9/13 #5D4509/10/10/13 #5D4509/10/11/13 #5D4509/10/12/13 #624A09/10/13/13 #624A09/10/14/13 #624A09/10/15/13 #624A09/10/16/13 #624A09/10/17/13 #5D4509/10/18/13 #5D4509/10/19/13 #4E3B09/10/20/13 #4A3709/10/21/13 #3E2E00/10/22/13 #372A00/10/23/13 #4A2C09/10/-3/14 #4A2C09/10/-2/14 #4A2C09/10/-1/14 #533F09/10/0/14 #442800/10/1/14 #876F3F/10/2/14 #876F3F/10/3/14 #876F3F/10/4/14 #533409/10/5/14 #624A09/10/6/14 #624A09/10/7/14 #624A09/10/8/14 #5D4509/10/9/14 #5D4509/10/10/14 #5D4509/10/11/14 #624A09/10/12/14 #624A09/10/13/14 #624A09/10/14/14 #5D4509/10/15/14 #584209/10/16/14 #533F09/10/17/14 #533F09/10/18/14 #876F3F/10/2/15 #876F3F/10/3/15 #876F3F/10/4/15 #876F3F/10/5/15 #624A09/10/6/15 #624A09/10/7/15 #624A09/10/8/15 #624A09/10/9/15 #5D4509/10/10/15 #5D4509/10/11/15 #5D4509/10/12/15 #584209/10/13/15 #533F09/10/14/15 #533409/10/2/16 #876F3F/10/3/16 #876F3F/10/4/16 #533409/10/5/16 #533409/10/6/16 #624A09/10/7/16 #624A09/10/8/16 #584209/10/9/16 #584209/10/10/16 #533F09/10/11/16 #533409/10/3/17 #876F3F/10/4/17 #533409/10/5/17 #624A09/10/6/17 #624A09/10/7/17 #533F09/10/8/17 #533409/10/4/18 #624A09/10/5/18 #584209/10/6/18");

        public final PixelModel model;

        Log(String fromString) {
            this.model = PixelModel.fromString(fromString);
        }
    }

    private enum FletchType {
        SHORT_BOW(new Rectangle(43, 380, 119, 72)),
        LONG_BOW(new Rectangle(200, 382, 122, 70)),
        CROSS_BOW(new Rectangle(363, 383, 106, 72));

        public final Rectangle bounds;

        FletchType(Rectangle bounds) {
            this.bounds = bounds;
        }
    }
}
