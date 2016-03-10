package filters;

/**
 * This enum essentially represents whether or not
 * you are comparing an object's value to before, equal,
 * or after another object's value.
 * 
 * Example usage:
 * 	Comparing two dates, we could specify that we
 * 	are checking to see if one date comes before, at, or after
 * 	the other.
 * 
 * @author Freddy
 *
 */
public enum Order
{
	BEFORE,
	EQUAL,
	AFTER;
}
