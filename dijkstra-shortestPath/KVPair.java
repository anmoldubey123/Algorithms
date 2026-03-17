/*
 * The following class is a struct used to store key-value pairs. Your heap
 * will make its comparisons using the keys.
 * 
 * The grader will overwrite this file, so any changes you make will be lost.
 * DO NOT MODIFY THIS FILE
 */
class KVPair
{
    int key;
    int value;

    public KVPair(int key, int value)
    {
        this.key = key;
        this.value = value;
    }

    public Boolean equals(KVPair other)
    {
        return other.key == this.key && other.value == this.value;
    }
}
