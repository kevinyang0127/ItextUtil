public class ParagraphUtil{
    
    public static void generateAnyVarParagraph(Document document, String str, ParagraphSettingInfo settingInfo, Map<String, Chunk> varMap) throws DocumentException {

        ChunkNode topNode = createChunkNode(new Chunk(str, settingInfo.getFont()), varMap);

        Paragraph para = new Paragraph();
        setParagraphByChunkBinaryTree(para, topNode);

        para.setFont(settingInfo.getFont());
        para.setAlignment(settingInfo.getAlignment());
        para.setLeading(settingInfo.getLeading());

        document.add(para);
    }

    public static Paragraph getAnyVarParagraph(String str, ParagraphSettingInfo settingInfo, Map<String, Chunk> varMap){

        ChunkNode topNode = createChunkNode(new Chunk(str, settingInfo.getFont()), varMap);

        Paragraph para = new Paragraph();
        setParagraphByChunkBinaryTree(para, topNode);

        para.setFont(settingInfo.getFont());
        para.setAlignment(settingInfo.getAlignment());
        para.setLeading(settingInfo.getLeading());

        return para;
    }

    private static ChunkNode createChunkNode(Chunk data, Map<String, Chunk> varMap){
        String str = data.getContent();
        Font font = data.getFont();
        ChunkNode node = new ChunkNode();

        for(String varKey : varMap.keySet()){
            if(str.contains(varKey)){
                String leftStr = str.split(varKey,2)[0];
                String rightStr = str.split(varKey,2)[1];
                node.dataChunk = varMap.get(varKey);
                node.leftNode = createChunkNode(new Chunk(leftStr,font), varMap);
                node.rightNode = createChunkNode(new Chunk(rightStr,font), varMap);
                break;
            }
        }

        if(node.dataChunk == null){
            node.dataChunk = data;
        }

        return node;
    }

    private static void setParagraphByChunkBinaryTree(Paragraph para, ChunkNode topNode){
        //順序: 左-中-右

        if(topNode.leftNode != null){
            setParagraphByChunkBinaryTree(para, topNode.leftNode);
        }

        if(topNode.dataChunk != null){
            para.add(topNode.dataChunk);
        }

        if(topNode.rightNode != null){
            setParagraphByChunkBinaryTree(para, topNode.rightNode);
        }

    }
}