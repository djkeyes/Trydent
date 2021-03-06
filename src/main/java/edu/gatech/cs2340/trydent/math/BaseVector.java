package edu.gatech.cs2340.trydent.math;

import java.util.Arrays;

/**
 * <strong>Developers using this library should use the Position and Vector
 * sub-classes rather than using this class directly. </strong>
 * <p>
 * This is the base vector class providing functionality required by points and
 * vectors. It's written using generics to allow end-users to easily intermingle
 * Positions and Vectors without code duplication, while keeping the concepts of
 * Positions and Vectors distinct to avoid code duplication.
 * <p>
 * <strong>All mathematical methods in this class, unless expressly stated
 * otherwise, are in-place (aka mutable), and return a reference to themselves.
 * </strong>
 * <p>
 * This is to enable convenient mathematical expressions, like:
 * <p>
 * {@code Position myPosition = new Position(3, 5).add(new Vector(5, 7)).scale(2.0);}
 * <p>
 * As well as equivalent expressions like:
 *
 * <pre>
 * Position myPosition = new Position(3, 5);
 * myPosition.add(new Vector(5, 7));
 * myPosition.scale(2.0);
 * </pre>
 *
 * @author Garrett Malmquist
 * @param <T>
 *            - generic type used for inheritance; developers using this library
 *            need not worry about it.
 */
public abstract class BaseVector<T extends BaseVector<T>> {

    private double[] values;

    // Used by toString()
    private String stringOpen = "<";
    private String stringClose = ">";
    private String stringDelimit = ", ";

    /**
     * Creates an new vector with the given number of components, initialized to
     * the zero vector.
     *
     * @param components
     *            the dimension of this vector (eg, 2 for 2D)
     */
    public BaseVector(int components) {
        values = new double[components];
    }

    /**
     * Creates a new 2D vector.
     */
    public BaseVector() {
        this(2); // Default to 2D
    }

    /**
     * Creates a copy of this vector.
     *
     * @return the copy
     */
    public abstract T copy();

    /**
     * Sets all components to 'value'.
     *
     * @param value
     *            floating point value
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T fill(double value) {
        Arrays.fill(values, value);
        return (T) this;
    }

    /**
     * Returns the number of components in this vector (aka the dimension of
     * this vector). Eg, for a 2D vector this will always be '2'.
     *
     * @return the number of components
     */
    public int getComponentCount() {
        return values.length;
    }

    /**
     * Returns the value of the ith component.
     *
     * Eg, 'x' is 0, 'y' is 1.
     *
     * @param i
     *            index of the desired component
     * @return the value of the desired component
     */
    public double getComponent(int i) {
        if (i < 0 || i >= values.length)
            throw new IllegalComponentException(i);
        return values[i];
    }

    /**
     * Sets the value of the ith component.
     *
     * @param i
     *            index of the component to set. E.g., 'x' is 0, 'y' is 1.
     * @param value
     *            the new value of the component
     */
    public void setComponent(int i, double value) {
        if (i < 0 || i >= values.length)
            throw new IllegalComponentException(i);
        values[i] = value;
    }

    /**
     * Returns the x-component. Equivalent to {@code getComponent(0)}.
     *
     * @return x
     */
    public double getX() {
        return values[0];
    }

    /**
     * Returns the y-component. Equivalent to {@code getComponent(1)}.
     *
     * @return y
     */
    public double getY() {
        return values[1];
    }

    /**
     * Sets the x-component. Equivalent to {@code setComponent(0, value)}.
     *
     * @param v
     *            the new value of x
     */
    public void setX(double v) {
        values[0] = v;
    }

    /**
     * Sets the y-component. Equivalent to {@code setComponent(1, value)}.
     *
     * @param v
     *            the new value of y
     */
    public void setY(double v) {
        values[1] = v;
    }

    /**
     * Sets the components of this vector equal to the input parameters.
     * <p>
     * Eg, {@code set(0.0, 0.5)} would set x=0.0, y=0.5.
     *
     * @param components
     *            (a variable number of double variables)
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T set(double... components) {
        for (int i = 0; i < components.length && i < values.length; i++) {
            values[i] = components[i];
        }
        return (T) this;
    }

    /**
     * Adds the second vector to this vector. I.e.:
     * <p>
     * {@code this = this + other}
     *
     * @param other
     *            - double values to add
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T add(double... other) {
        checkComponents(other);
        for (int i = 0; i < values.length && i < other.length; i++) {
            values[i] += other[i];
        }
        return (T) this;
    }

    /**
     * Adds the second vector to this vector. I.e.:
     * <p>
     * {@code this = this + other}
     *
     * @param other
     *            - other vector
     * @return the reference to this vector for method chaining convenience
     */
    public T add(BaseVector<?> other) {
        return add(other.values);
    }

    /**
     * Adds the second vector, scaled by 'scale', to this vector. I.e.:
     * <p>
     * this = this + (scale * other).
     *
     * @param scale
     *            - scalar number
     * @param other
     *            - other vector
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T add(double scale, BaseVector<?> other) {
        checkComponents(other);
        for (int i = 0; i < values.length && i < other.values.length; i++) {
            values[i] += scale * other.values[i];
        }
        return (T) this;
    }

    /**
     * Subtracts the second vector from this vector. I.e.:
     * <p>
     * {@code this = this - other}
     *
     * @param other
     *            - double values to subtract
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T subtract(double... other) {
        checkComponents(other);
        for (int i = 0; i < values.length && i < other.length; i++) {
            values[i] -= other[i];
        }
        return (T) this;
    }

    /**
     * Subtracts the second vector from this vector. I.e.:
     * <p>
     * {@code this = this - other}
     *
     * @param other
     *            - other vector to subtract
     * @return the reference to this vector for method chaining convenience
     */
    public T subtract(BaseVector<?> other) {
        return subtract(other.values);
    }

    /**
     * Subtracts the second vector, scaled by 'scale', from this vector. I.e.:
     * <p>
     * this = this - (scale * other).
     *
     * @param scale
     *            scalar number
     * @param other
     *            other vector
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T subtract(double scale, BaseVector<?> other) {
        checkComponents(other);
        for (int i = 0; i < values.length && i < other.values.length; i++) {
            values[i] -= scale * other.values[i];
        }
        return (T) this;
    }

    /**
     * Scales this vectors components by the given amounts. If only one number
     * is passed, it will scale all components by the same value.
     * <p>
     * Example usages:
     *
     * <pre>
     * {@code
     * this.scale(0.5) // scales whole vector by 0.5
     * this.scale(0.5, 0.75) // scales x by 0.5 and y by 0.75.
     * }
     * </pre>
     *
     * @param scale
     *            the values to element-wise multiply this vector by
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T scale(double... scale) {
        if (scale.length == 1) {
            for (int i = 0; i < values.length; i++)
                values[i] *= scale[0];
        } else {
            for (int i = 0; i < values.length && i < scale.length; i++) {
                values[i] *= scale[i];
            }
        }
        return (T) this;
    }

    /**
     * Scales the components of this vector by the components of the other
     * vector. I.e.,
     *
     * <pre>
     * {@code
     * this.x = this.x * other.x;
     * this.y = this.y * other.y;
     * }
     * </pre>
     *
     * @param other
     *            the vector to element-wise multiply the components of this
     *            vector by
     * @return the reference to this vector for method chaining convenience
     */
    public T scale(BaseVector<?> other) {
        return scale(other.values);
    }

    /**
     * Rotates this vector's x,y components counter-clockwise.
     * <p>
     * (In the case of points, rotates in 2D about the origin).
     *
     * @param angle
     *            amount to rotate by, in degrees.
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T rotate2D(double angle) {
        if (magnitudeSquared() == 0)
            return (T) this;

        double theta = Math.atan2(getY(), getX());

        angle = Math.toRadians(angle);
        double m = magnitude();
        double c = Math.cos(theta + angle);
        double s = Math.sin(theta + angle);

        return set(m * c, m * s);
    }

    /**
     * Rotates this vector 90 counter-clockwise in 2D.
     *
     * @return the reference to this vector for method chaining convenience
     */
    public T rotate90() {
        return set(-getY(), getX());
    }

    /**
     * Returns the magnitude (aka length, aka norm) of this vector.
     *
     * @return the magnitude
     */
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    /**
     * Returns the squared magnitude (magnitude^2) of this vector.
     *
     * @return the squared magnitude
     */
    public double magnitudeSquared() {
        return dot(this);
    }

    /**
     * Normalizes this vector (aka makes it unit length, aka makes its length =
     * 1).
     *
     * @return the reference to this vector for method chaining convenience
     */
    @SuppressWarnings("unchecked")
    public T normalize() {
        double m2 = magnitudeSquared();
        if (m2 == 0)
            return (T) this; // Can't normalize the 0-vector.
        return scale(1.0 / Math.sqrt(m2));
    }

    /**
     * Returns the linear interpolation between this vector and the other vector
     * by an amount t. (Advanced functionality).
     *
     * @param t
     *            interpolation parameter (0 will give this vector, 1 will give
     *            the other vector, 0.5 is the midpoint.)
     * @param other
     *            the other vector
     * @return the interpolated point
     */
    @SuppressWarnings("unchecked")
    public T lerp(double t, BaseVector<?> other) {
        checkComponents(other);
        for (int i = 0; i < values.length && i < other.values.length; i++) {
            values[i] = (1.0 - t) * values[i] + t * other.values[i];
        }
        return (T) this;
    }

    /**
     * Returns the dot-product between the two vectors.
     *
     * @param other
     *            the other vector
     * @return the dot-product
     */
    public double dot(BaseVector<?> other) {
        return dot(other.values);
    }

    /**
     * Returns the dot-product between the two vectors.
     *
     * @param other
     *            other vector, specified as a list of doubles.
     * @return the dot-product
     */
    public double dot(double... other) {
        checkComponents(other);
        double total = 0;
        for (int i = 0; i < values.length && i < other.length; i++) {
            total += values[i] * other[i];
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(stringOpen);
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                sb.append(stringDelimit);
            double value = values[i];
            if (Math.abs(value) < 1e-6)
                sb.append("0.0");
            else {
                value = Math.round(value * 1.0e6) / (1.0e6);
                sb.append(value);
            }
        }
        sb.append(stringClose);
        return sb.toString();
    }

    protected void setStringOpen(String open) {
        this.stringOpen = open;
    }

    protected void setStringClose(String close) {
        this.stringClose = close;
    }

    protected void setStringDelimit(String delimit) {
        this.stringDelimit = delimit;
    }

    private void checkComponents(double... values) {
        if (values.length != this.values.length)
            throw new VectorMismatchException("Number of components must be " + this.values.length + ", got "
                    + values.length);
    }

    private void checkComponents(BaseVector<?> vector) {
        checkComponents(vector.values);
    }

}
