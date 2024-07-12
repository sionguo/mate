// @ts-ignore
import React from 'react';
import type { Node, NodeProps } from '@xyflow/react';
import { Handle, Position } from '@xyflow/react';

type HttpNode = Node<{ method: string }, 'http'>;
const HttpNodeComp = ({ data }: NodeProps<HttpNode>) => {
  return (
    <>
      <Handle type="target" position={Position.Top}></Handle>
      <div>A special number: {data.method}</div>
      <Handle type="source" position={Position.Bottom}></Handle>
    </>
  );
};

export default HttpNodeComp;
