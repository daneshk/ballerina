/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerinalang.compiler.syntax.tree;

import io.ballerinalang.compiler.internal.parser.tree.STNode;

import java.util.Objects;

/**
 * This is a generated syntax tree node.
 *
 * @since 2.0.0
 */
public class MarkdownDocumentationNode extends DocumentationNode {

    public MarkdownDocumentationNode(STNode internalNode, int position, NonTerminalNode parent) {
        super(internalNode, position, parent);
    }

    public NodeList<Node> documentationLines() {
        return new NodeList<>(childInBucket(0));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T apply(NodeTransformer<T> visitor) {
        return visitor.transform(this);
    }

    @Override
    protected String[] childNames() {
        return new String[]{
                "documentationLines"};
    }

    public MarkdownDocumentationNode modify(
            NodeList<Node> documentationLines) {
        if (checkForReferenceEquality(
                documentationLines.underlyingListNode())) {
            return this;
        }

        return NodeFactory.createMarkdownDocumentationNode(
                documentationLines);
    }

    public MarkdownDocumentationNodeModifier modify() {
        return new MarkdownDocumentationNodeModifier(this);
    }

    /**
     * This is a generated tree node modifier utility.
     *
     * @since 2.0.0
     */
    public static class MarkdownDocumentationNodeModifier {
        private final MarkdownDocumentationNode oldNode;
        private NodeList<Node> documentationLines;

        public MarkdownDocumentationNodeModifier(MarkdownDocumentationNode oldNode) {
            this.oldNode = oldNode;
            this.documentationLines = oldNode.documentationLines();
        }

        public MarkdownDocumentationNodeModifier withDocumentationLines(
                NodeList<Node> documentationLines) {
            Objects.requireNonNull(documentationLines, "documentationLines must not be null");
            this.documentationLines = documentationLines;
            return this;
        }

        public MarkdownDocumentationNode apply() {
            return oldNode.modify(
                    documentationLines);
        }
    }
}
