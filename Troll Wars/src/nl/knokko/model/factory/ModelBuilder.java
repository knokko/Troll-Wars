package nl.knokko.model.factory;

import java.util.ArrayList;
import java.util.Random;

import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.util.Maths;
import nl.knokko.util.collection.floating.ArrayFloatList;
import nl.knokko.util.collection.floating.FloatList;

public class ModelBuilder {
	
	public FloatList vertices = new ArrayFloatList(20);
	private FloatList normals = new ArrayFloatList(20);
	private FloatList textures = new ArrayFloatList(20);
	private ArrayList<Integer> indices = new ArrayList<Integer>();
	
	public ModelBuilder() {
		
	}
	
	public int addVertex(float x, float y, float z, float nx, float ny, float nz, float u, float v){
		vertices.addAll(x, y, z);
		normals.addAll(nx, ny, nz);
		textures.addAll(u, v);
		return vertexCount() - 1;
	}
	
	public void addVerticalCilinder(int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float maxY, TextureArea area, int tw, int th){
		addVerticalCilinder(flatParts, centreX, centreZ, radiusX, radiusZ, minY, maxY, area.getMinU(tw), area.getMinV(th), area.getMaxU(tw), area.getMaxV(th));
	}
	
	public void addVerticalCilinder(int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float maxY, float minU, float minV, float maxU, float maxV){
		int index = vertexCount();
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			float x = centreX + Maths.sin(angle) * radiusX;
			float z = centreZ - Maths.cos(angle) * radiusZ;
			addVertex(x, minY, z, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, minV);
			addVertex(x, maxY, z, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, maxV);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	public void addVerticalCilinder(int flatParts, float centreX, float centreZ, float radiusX1, float radiusZ1, float radiusX2, float radiusZ2, float y1, float y2, float minU, float v1, float maxU, float v2){
		int index = vertexCount();
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			addVertex(centreX + Maths.sin(angle) * radiusX1, y1, centreZ - Maths.cos(angle) * radiusZ1, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, v1);
			addVertex(centreX + Maths.sin(angle) * radiusX2, y2, centreZ - Maths.cos(angle) * radiusZ2, Maths.sin(angle), 0, Maths.cos(angle), minU + deltaU * i / flatParts, v2);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	public void addHorizontalCilinder(int flatParts, float centreX, float centreY, float radiusX1, float radiusY1, float radiusX2, float radiusY2, float z1, float z2, float minU, float v1, float maxU, float v2){
		int index = vertexCount();
		float deltaU = maxU - minU;
		for(int i = 0; i <= flatParts; i++){
			float angle = i * 360f / flatParts;
			addVertex(centreX + Maths.cos(angle) * radiusX1, centreY + Maths.sin(angle) * radiusY1, z1, Maths.cos(angle), Maths.sin(angle), 0f, minU + deltaU * i / flatParts, v1);
			addVertex(centreX + Maths.cos(angle) * radiusX2, centreY + Maths.sin(angle) * radiusY2, z2, Maths.cos(angle), Maths.sin(angle), 0f, minU + deltaU * i / flatParts, v2);
		}
		for(int i = 0; i < flatParts; i++)
			bindFourangle(index + 2 * i, index + 2 * i + 1, index + 2 * i + 3, index + 2 * i + 2);
	}
	
	public void addSmoothTopsCilinder(int flatParts, float centreX, float centreZ, float radiusX, float radiusZ, float minY, float minCilY, float maxY, float maxCilY, float minU, float minV, float maxU, float maxV){
		int index = vertexCount();
		float deltaV = (maxV - minV) * 0.5f;
		float midV = (minV + maxV) / 2;
		float midY = (minY + maxY) / 2;
		float midCilY = (minCilY + maxCilY) / 2;
		addVerticalCilinder(flatParts, centreX, centreZ, radiusX, radiusZ, minCilY, maxCilY, minU, midV - deltaV * ((midCilY - minCilY) / (midY - minY)), maxU, midV + deltaV * ((maxCilY - midCilY) / (maxY - midY)));
		float midU = (minU + maxU) / 2;
		int indexTop = vertexCount();
		addVertex(centreX, maxY, centreZ, 0f, 1f, 0f, midU, maxV);
		addVertex(centreX, minY, centreZ, 0f, -1f, 0f, midU, minV);
		for(int i = 0; i < flatParts; i++){
			bindTriangle(index + 2 * i, index + 2 * i + 2, indexTop + 1);
			bindTriangle(index + 2 * i + 1, index + 2 * i + 3, indexTop);
		}
	}
	
	public void addSphere(int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = vertexCount();
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = (float)j / parts * 360f;
				addVertex(centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	public void addSphereTop(int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = vertexCount();
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = (float)j / parts * 360f;
				addVertex(centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	public void addSphereSouth(int parts, float centreX, float centreY, float centreZ, float radiusX, float radiusY, float radiusZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = vertexCount();
		for(int i = 0; i <= parts; i++){
			float angleI = Maths.asin((float)i / parts * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= parts; j++){
				float angleJ = 0 + (float)j / parts * 180f;
				addVertex(centreX + Maths.cos(angleJ) * c * radiusX, centreY + s * radiusY, centreZ + Maths.sin(angleJ) * c * radiusZ, Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / parts * deltaU, minV + (float)i / parts * deltaV);
			}
		}
		for(int i = 0; i <= parts; i++)
			for(int j = 0; j <= parts; j++)
				bindFourangle(index + j * parts + i, index + j * parts + i + 1, index + (j + 1) * parts + i + 1, index + (j + 1) * parts + i);
	}
	
	public void addBoneSphere(ModelBone bone, Random random, float scale, float centreX, float centreY, float centreZ, float minU, float minV, float maxU, float maxV, int partsY, int partsR){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		int index = vertexCount();
		for(int i = 0; i <= partsY; i++){
			float angleI = Maths.asin((float)i / partsY * 2 - 1);
			float c = Maths.cos(angleI);
			float s = Maths.sin(angleI);
			for(int j = 0; j <= partsR; j++){
				float angleJ = (float)j / partsR * 360f;
				addVertex(centreX + Maths.cos(angleJ) * c * bone.boneRadiusTopX() * scale * (1 - bone.maxRandomFactor() * 0.5f + 1.0f * random.nextFloat() * bone.maxRandomFactor()), centreY + s * bone.boneRadiusTopY() * scale, centreZ + Maths.sin(angleJ) * c * bone.boneRadiusTopZ() * scale * (1 - bone.maxRandomFactor() * 0.5f + 1.0f * random.nextFloat() * bone.maxRandomFactor()), Maths.cos(angleJ) * c, s, Maths.sin(angleJ) * c, minU + (float)j / partsR * deltaU, minV + (float)i / partsR * deltaV);
			}
		}
		for(int i = 0; i <= partsY; i++)
			for(int j = 0; j <= partsR; j++)
				bindFourangle(index + j * partsR + i, index + j * partsR + i + 1, index + (j + 1) * partsR + i + 1, index + (j + 1) * partsR + i);
	}
	
	public void addBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float minU, float minV, float maxU, float maxV){
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float deltaX = maxX - minX;
		float deltaY = maxY - minY;
		float deltaZ = maxZ - minZ;
		float sepU1 = deltaX / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float sepU2 = (deltaX + deltaZ) / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float sepU3 = (2 * deltaX + deltaZ) / (2 * deltaX + 2 * deltaZ) * deltaU + minU;
		float midU = minU + deltaU / 2;
		float sepV = deltaY / (deltaY + deltaZ) * deltaV + minV;
		addPlane(0, 0, -1, minX, minY, minZ, minU, minV, maxX, minY, minZ, sepU1, minV, maxX, maxY, minZ, sepU1, sepV, minX, maxY, minZ, minU, sepV);//north
		addPlane(1, 0, 0, maxX, minY, minZ, sepU1, minV, maxX, minY, maxZ, sepU2, minV, maxX, maxY, maxZ, sepU2, sepV, maxX, maxY, minZ, sepU1, sepV);//east
		addPlane(0, 0, 1, maxX, minY, maxZ, sepU2, minV, minX, minY, maxZ, sepU3, minV, minX, maxY, maxZ, sepU3, sepV, maxX, maxY, maxZ, sepU2, sepV);//south
		addPlane(-1, 0, 0, minX, minY, maxZ, sepU3, minV, minX, minY, minZ, maxU, minV, minX, maxY, minZ, maxU, sepV, minX, maxY, maxZ, sepU3, sepV);//west
		addPlane(0, 1, 0, minX, maxY, maxZ, minU, sepV, maxX, maxY, maxZ, midU, sepV, maxX, maxY, minZ, midU, maxV, minX, maxY, minZ, minU, maxV);//up
		addPlane(0, -1, 0, minX, minY, maxZ, minU, sepV, maxX, minY, maxZ, midU, sepV, maxX, minY, minZ, midU, maxV, minX, minY, minZ, minU, maxV);//down
	}
	
	public void addFourangle(TextureArea area, int tw, int th, 
			float x1, float y1, float z1, float nx1, float ny1, float nz1,
			float x2, float y2, float z2, float nx2, float ny2, float nz2,
			float x3, float y3, float z3, float nx3, float ny3, float nz3,
			float x4, float y4, float z4, float nx4, float ny4, float nz4){
		addFourangle(x1, y1, z1, nx1, ny1, nz1, area.getMinU(tw), area.getMinV(th), x2, y2, z2, nx2, ny2, nz2, area.getMaxU(tw), area.getMinV(th),
				x3, y3, z3, nx3, ny3, nz3, area.getMaxU(tw), area.getMaxV(th), x4, y4, z4, nx4, ny4, nz4, area.getMinU(tw), area.getMaxV(th));
	}
	
	public void addFourangle(float x1, float y1, float z1, float nx1, float ny1, float nz1, float u1, float v1, float x2, float y2, float z2, float nx2, float ny2, float nz2, float u2, float v2, float x3, float y3, float z3, float nx3, float ny3, float nz3, float u3, float v3, float x4, float y4, float z4, float nx4, float ny4, float nz4, float u4, float v4){
		addVertex(x1, y1, z1, nx1, ny1, nz1, u1, v1);
		addVertex(x2, y2, z2, nx2, ny2, nz2, u2, v2);
		addVertex(x3, y3, z3, nx3, ny3, nz3, u3, v3);
		addVertex(x4, y4, z4, nx4, ny4, nz4, u4, v4);
		bindFourangle();
	}
	
	public void addPlane(TextureArea area, int tw, int th, float nx, float ny, float nz, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4){
		addPlane(nx, ny, nz, x1, y1, z1, area.getMinU(tw), area.getMinV(th), x2, y2, z2, area.getMaxU(tw), area.getMinV(th), x3, y3, z3, area.getMaxU(tw), area.getMaxV(th), x4, y4, z4, area.getMinU(tw), area.getMaxV(th));
	}
	
	public void addPlane(float nx, float ny, float nz, float x1, float y1, float z1, float u1, float v1, float x2, float y2, float z2, float u2, float v2, float x3, float y3, float z3, float u3, float v3, float x4, float y4, float z4, float u4, float v4){
		addFourangle(x1, y1, z1, nx, ny, nz, u1, v1, x2, y2, z2, nx, ny, nz, u2, v2, x3, y3, z3, nx, ny, nz, u3, v3, x4, y4, z4, nx, ny, nz, u4, v4);
	}
	
	public void addPlaneTriangle(float nx, float ny, float nz,
			float x1, float y1, float z1, float u1, float v1,
			float x2, float y2, float z2, float u2, float v2,
			float x3, float y3, float z3, float u3, float v3){
		addTriangle(x1, y1, z1, nx, ny, nz, u1, v1, x2, y2, z2, nx, ny, nz, u2, v2, x3, y3, z3, nx, ny, nz, u3, v3);
	}
	
	public void addTriangle(float x1, float y1, float z1, float nx1, float ny1, float nz1, float u1, float v1, float x2, float y2, float z2, float nx2, float ny2, float nz2, float u2, float v2, float x3, float y3, float z3, float nx3, float ny3, float nz3, float u3, float v3){
		addVertex(x1, y1, z1, nx1, ny1, nz1, u1, v1);
		addVertex(x2, y2, z2, nx2, ny2, nz2, u2, v2);
		addVertex(x3, y3, z3, nx3, ny3, nz3, u3, v3);
		bindTriangle();
	}
	
	public void bindTriangle(){
		int i = vertexCount();
		bindTriangle(i - 2, i - 1, i);
	}
	
	public void bindTriangle(int indice1, int indice2, int indice3){
		indices.add(indice1);
		indices.add(indice2);
		indices.add(indice3);
	}
	
	public void bindFourangle(int indice1, int indice2, int indice3, int indice4){
		bindTriangle(indice1, indice2, indice3);
		bindTriangle(indice1, indice4, indice3);
	}
	
	public void bindFourangle2(int indice1, int indice2, int indice3, int indice4){
		bindTriangle(indice1, indice2, indice4);
		bindTriangle(indice1, indice3, indice4);
	}
	
	public void bindFourangle(){
		int i = vertexCount();
		bindFourangle(i - 4, i - 3, i - 2, i - 1);
	}
	
	public void mirrorX(){
		for(int i = 0; i < vertices.size(); i += 3)
			vertices.set(i, -vertices.get(i));
		for(int i = 0; i < normals.size(); i+= 3)
			normals.set(i, -normals.get(i));
	}
	
	public void mirrorY(){
		for(int i = 1; i < vertices.size(); i += 3)
			vertices.set(i, -vertices.get(i));
		for(int i = 1; i < normals.size(); i+= 3)
			normals.set(i, -normals.get(i));
	}
	
	public void mirrorZ(){
		for(int i = 2; i < vertices.size(); i += 3)
			vertices.set(i, -vertices.get(i));
		for(int i = 2; i < normals.size(); i+= 3)
			normals.set(i, -normals.get(i));
	}
	
	public int vertexCount() {
		return vertices.size() / 3;
	}
	
	public AbstractModel load(){
		return ModelLoader.loadDefaultModel(vertices, normals, textures, indices);
	}
}