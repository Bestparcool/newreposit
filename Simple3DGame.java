// File: Simple3DGame.java
// A simple 3D collect game using jMonkeyEngine

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class Simple3DGame extends SimpleApplication implements ActionListener {

    private Geometry player;
    private Geometry target;
    private int score = 0;
    private BitmapText scoreText;
    private BitmapText winText;

    public static void main(String[] args) {
        Simple3DGame app = new Simple3DGame();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cam.setLocation(new Vector3f(5f, 5f, 10f));
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        Box playerBox = new Box(0.5f, 0.5f, 0.5f);
        player = new Geometry("Player", playerBox);
        Material playerMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        playerMat.setColor("Color", ColorRGBA.Red);
        player.setMaterial(playerMat);
        player.setLocalTranslation(0, 0.5f, 0);
        rootNode.attachChild(player);

        Sphere sphere = new Sphere(16, 16, 0.5f);
        target = new Geometry("Target", sphere);
        Material targetMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        targetMat.setColor("Color", ColorRGBA.Yellow);
        target.setMaterial(targetMat);
        target.setLocalTranslation(3, 0.5f, 3);
        rootNode.attachChild(target);

        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(24);
        scoreText.setColor(ColorRGBA.White);
        scoreText.setText("Score: 0");
        scoreText.setLocalTranslation(10, getCamera().getHeight() - 20, 0);
        guiNode.attachChild(scoreText);

        winText = new BitmapText(guiFont, false);
        winText.setSize(32);
        winText.setColor(ColorRGBA.Green);
        winText.setText("");
        winText.setLocalTranslation(getCamera().getWidth() / 2 - 100, getCamera().getHeight() / 2, 0);
        guiNode.attachChild(winText);

        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this, "Left", "Right", "Forward", "Back");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            Vector3f pos = player.getLocalTranslation();
            float speed = 0.2f;
            switch (name) {
                case "Left": pos.x -= speed; break;
                case "Right": pos.x += speed; break;
                case "Forward": pos.z -= speed; break;
                case "Back": pos.z += speed; break;
            }
            pos.x = Math.max(-5, Math.min(5, pos.x));
            pos.z = Math.max(-5, Math.min(5, pos.z));
            player.setLocalTranslation(pos);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        Ray ray = new Ray(player.getLocalTranslation(), Vector3f.UNIT_Y);
        CollisionResults results = new CollisionResults();
        target.collideWith(ray, results);
        
        if (results.size() > 0 && player.getLocalTranslation().distance(target.getLocalTranslation()) < 1.2f) {
            score++;
            scoreText.setText("Score: " + score);
            
            float x = (float) (Math.random() * 10 - 5);
            float z = (float) (Math.random() * 10 - 5);
            target.setLocalTranslation(x, 0.5f, z);
            
            if (score >= 5) {
                winText.setText("YOU WIN! Press ESC to exit.");
                if (inputManager.isKeyPressed(KeyInput.KEY_ESCAPE)) {
                    stop();
                }
            }
        }
    }
}


'''Зимнее утро

Мороз и солнце; день чудесный!
Еще ты дремлешь, друг прелестный —
Пора, красавица, проснись:
Открой сомкнуты негой взоры
Навстречу северной Авроры,
Звездою севера явись!
Вечор, ты помнишь, вьюга злилась,
На мутном небе мгла носилась;
Луна, как бледное пятно,
Сквозь тучи мрачные желтела,
И ты печальная сидела —
А нынче... погляди в окно:
Под голубыми небесами
Великолепными коврами,
Блестя на солнце, снег лежит;
Прозрачный лес один чернеет,
И ель сквозь иней зеленеет,
И речка подо льдом блестит.
Вся комната янтарным блеском
Озарена. Веселым треском
Трещит затопленная печь.
Приятно думать у лежанки.
Но знаешь: не велеть ли в санки
Кобылку бурую запречь?
Скользя по утреннему снегу,
Друг милый, предадимся бегу
Нетерпеливого коня
И навестим поля пустые,
Леса, недавно столь густые,
И берег, милый для меня.'''
