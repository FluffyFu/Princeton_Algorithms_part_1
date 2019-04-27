"""
Implement an algorithm to solve 3SUM problem (sorted distinct array) in quadratic time.
Idea: First solve 2SUM problem within linear time. Then for each element c in
the sorted array, use the 2SUM algorithm to find index i and j such that a[i] +
a[j] == -c.
"""

def two_sum(sorted_list, c):
    """
    Find the index i, j such that a[i] + a[j] = c, where a is a sorted list
    without duplicates.

    Each element can only be used once and there can be multiple pairs.

    Parameters:
        sorted_list(list): a sorted list
        c(integer): target of the 2 SUM

    Returns:
        results(list): a list of tuples contains a pair of list index.
    >>> two_sum([1, 2, 3, 4, 7], 6)
    [(1, 3)]
    >>> two_sum([1, 4, 9], 7)
    []
    """
    collect = {}
    results = []
    for i, element in enumerate(sorted_list):
        collect[element] = i
        other = c - element
        if (other in collect) and (other != element):
            j = collect[other]
            results.append((j, i))

    return results

def three_sum(sorted_list, target=0):
    """
    Find if there exists 3 elements in the list which sums to zero. Each
    element cannot be used twice.

    Current implementation only finds one triplet. Further implementation
    should capture all the triplets without duplications.

    Parameters:
        sorted_list(list): a list of sorted distinct integers.

        target(integer): 3SUM target (default is zero).

    Returns:
        results: a list of tuples contain a pair of feasible index.

    >>> three_sum([1, 2, 4, -3])
    (0, 1, 3)
    """
    for first_index, first_element in enumerate(sorted_list):
        sub_results = two_sum(sorted_list, target-first_element)
        if sub_results:
            second_index = sub_results[0][0]
            third_index = sub_results[0][1]
            return (first_index, second_index, third_index)

if __name__ == '__main__':
    import doctest
    doctest.testmod()

