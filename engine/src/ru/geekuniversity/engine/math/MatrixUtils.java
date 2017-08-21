package ru.geekuniversity.engine.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class MatrixUtils {

    private MatrixUtils() {

    }

    public static void method() {
        Vector2 vector2 = new Vector2(0f, 0f);
        Matrix3 matrix3 = new Matrix3();
        matrix3.idt().translate(1f, 1f).scale(2f, 2f);
        vector2.mul(matrix3);
        System.out.println(vector2);

        Vector3 vector3 = new Vector3();
        Matrix4 matrix4 = new Matrix4();
        matrix4.idt().scale(1f, 2f, 4f).translate(10,3,4);
        vector3.mul(matrix4);

    }
}
