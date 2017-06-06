package ru.javabegin.training.android6.finance.core.utils;

import java.util.List;

import ru.javabegin.training.android6.finance.core.interfaces.TreeNode;

// построитель дерева
public class TreeUtils<T extends TreeNode> {


    // встраивает новый элемент в нужное место дерева: суть в том, что нужно найти родительский элемент для объекта newNode
    public boolean addToTree(long parentId, T newNode, List<T> list) {
        if (parentId != 0) {
            for (T currentNode : list) {// искать сначала во всех корневых объектах
                if (currentNode.getId() == parentId) {
                    currentNode.add(newNode);
                    return true;
                } else {// если среди корневых элементов не найдены родители
                    T node = recursiveSearch(parentId, currentNode);// проходим по всем уровням дочерних элементов
                    if (node != null) {// если нашли среди дочерних элементов
                        node.add(newNode);
                        return true;
                    }
                }
            }
        }

        list.add(newNode);// если не найден родительский элемент - добавляем как корневой

        return false;
    }



    // рекурсивно проходит по всем дочерним элементам
    private T recursiveSearch(long parentId, T child) {
        for (T node : (List<T>)child.getChilds()) {
            if (node.getId() == parentId) {
                return node;
            } else if (node.hasChilds()) {// если у текущего узло есть свои дочерние элемента - проходим и по ним
                recursiveSearch(parentId, node);
            }
        }
        return null;
    }
}
