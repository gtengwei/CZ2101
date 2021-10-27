def knapsack_dynamic():



def knapsack_recursive(c: int, item_index: int, profits: list[int], weights: list[int]) -> int:
    # If the max weight is 0 or we have run out of items to pick, return 0
    if c == 0 or item_index == len(profits):
        return 0

    # If the weight of the current item is more than maximum weight allowed, we cannot pick it,
    # so we move on to the next element.
    if weights[item_index] > c:
        return knapsack_recursive(c, item_index + 1, profits, weights)

    # We choose whether or not to pick the item.
    return max(
        # We pick the item
        knapsack_recursive(c - weights[item_index], item_index, profits, weights) + profits[item_index],
        # We do not pick the item
        knapsack_recursive(c, item_index + 1, profits, weights)
    )


def main():
    c = 14  # Max possible weight

    profits = [7, 6, 9]
    weights1 = [4, 6, 8]
    weights2 = [5, 6, 8]

    print(f"Max profit calculated by recursive: {knapsack_recursive(14, 0, profits, weights1)}")


if __name__ == '__main__':
    main()
